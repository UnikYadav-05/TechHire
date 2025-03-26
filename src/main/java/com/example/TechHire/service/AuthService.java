package com.example.TechHire.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.TechHire.model.LoginRequest;
import com.example.TechHire.model.RegisterRequest;

import java.util.*;

@Service
public class AuthService {

    private final String KEYCLOAK_URL = "http://localhost:8080/realms/TechHire/protocol/openid-connect/token";
    private final String CLIENT_ID = "techhire-backend";
    private final String CLIENT_SECRET = "sov5dBqt5XSqMEaTK9YXafmPsUsIqLAi";
    private final String KEYCLOAK_ADMIN_URL = "http://localhost:8080/admin/realms/TechHire/users";
    private final String KEYCLOAK_ROLE_URL = "http://localhost:8080/admin/realms/TechHire/roles";

    private final RestTemplate restTemplate = new RestTemplate();

    // 🔹 1️⃣ Login User
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET); // Add client secret
        body.add("grant_type", "password");
        body.add("username", loginRequest.getEmail());
        body.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    KEYCLOAK_URL, // Use the correct Keycloak token endpoint
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("✅ Login Successful: " + response.getBody());
                return response;
            } else {
                System.out.println("❌ Login Failed: " + response.getBody());
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
        } catch (HttpClientErrorException e) {
            System.out.println("❌ Keycloak Error: " + e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }






    // 🔹 2️⃣ Register User (With Role Assignment)
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // ✅ Fetch admin token dynamically
        String adminToken = getAdminToken();
        headers.setBearerAuth(adminToken);

        Map<String, Object> user = new HashMap<>();
        user.put("username", registerRequest.getUsername());
        user.put("email", registerRequest.getEmail());
        user.put("enabled", true);

        // ✅ Add Password Credentials
        List<Map<String, Object>> credentials = new ArrayList<>();
        Map<String, Object> passwordMap = new HashMap<>();
        passwordMap.put("type", "password");
        passwordMap.put("value", registerRequest.getPassword());
        passwordMap.put("temporary", false);
        credentials.add(passwordMap);

        user.put("credentials", credentials);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(KEYCLOAK_ADMIN_URL, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("✅ User Registered: " + registerRequest.getEmail());
            String userId = getUserId(registerRequest.getUsername());
            assignRoleToUser(userId, registerRequest.getRole());
        } else {
            System.out.println("❌ User Registration Failed: " + response.getBody());
        }

        return response;
    }

    // 🔹 3️⃣ Get User ID from Keycloak
    private String getUserId(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAdminToken());

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String url = KEYCLOAK_ADMIN_URL + "?username=" + username;

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, List.class);
        if (response.getBody() != null && !response.getBody().isEmpty()) {
            Map<String, Object> user = (Map<String, Object>) response.getBody().get(0);
            return (String) user.get("id");
        }
        return null;
    }

    // 🔹 4️⃣ Assign Role to User (FIXED)
    private void assignRoleToUser(String userId, String roleName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAdminToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // ✅ Fetch Role ID Dynamically
        String roleId = getRoleId(roleName);
        if (roleId == null) {
            System.out.println("❌ Role Not Found: " + roleName);
            return;
        }

        // ✅ Prepare Role Assignment Data
        List<Map<String, Object>> roles = new ArrayList<>();
        Map<String, Object> role = new HashMap<>();
        role.put("id", roleId);
        role.put("name", roleName);
        roles.add(role);

        HttpEntity<List<Map<String, Object>>> assignRoleRequest = new HttpEntity<>(roles, headers);
        String assignRoleUrl = KEYCLOAK_ADMIN_URL + "/" + userId + "/role-mappings/realm";

        restTemplate.exchange(assignRoleUrl, HttpMethod.POST, assignRoleRequest, String.class);
        System.out.println("✅ Assigned Role: " + roleName + " to User ID: " + userId);
    }

    // 🔹 5️⃣ Get Role ID (NEW FIX)
    private String getRoleId(String roleName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAdminToken());

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(KEYCLOAK_ROLE_URL, HttpMethod.GET, requestEntity, List.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            for (Object roleObj : response.getBody()) {
                Map<String, Object> role = (Map<String, Object>) roleObj;
                if (role.get("name").equals(roleName)) {
                    return (String) role.get("id");
                }
            }
        }
        return null;
    }

    // 🔹 6️⃣ Get Admin Token (Client Credentials Flow) ✅ FIXED
    private String getAdminToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET +
                "&grant_type=client_credentials";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(KEYCLOAK_URL, HttpMethod.POST, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String token = response.getBody().get("access_token").toString();
            System.out.println("🔑 Fetched Admin Token: " + token);
            return token;
        } else {
            throw new RuntimeException("❌ Failed to get admin token: " + response.getBody());
        }
    }
}
