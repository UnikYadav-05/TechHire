package com.example.TechHire.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.TechHire.model.LoginRequest;
import com.example.TechHire.model.RegisterRequest;

import java.util.*;

@Service
public class AuthService {

    private final String KEYCLOAK_URL = "http://localhost:8080/realms/TechHire/protocol/openid-connect/token";
    private final String CLIENT_ID = "techhire-backend";
    private final String CLIENT_SECRET = "bECDj192K8BnMzOOZiuR60ShoNxxZtzF";
    private final String KEYCLOAK_ADMIN_URL = "http://localhost:8080/admin/realms/TechHire/users";
    private final String KEYCLOAK_ROLE_URL = "http://localhost:8080/admin/realms/TechHire/roles";

    private final RestTemplate restTemplate = new RestTemplate();

    // 🔹 1️⃣ Login User
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = UriComponentsBuilder.newInstance()
                .queryParam("client_id", CLIENT_ID)
                .queryParam("client_secret", CLIENT_SECRET)
                .queryParam("grant_type", "password")
                .queryParam("username", loginRequest.getEmail())
                .queryParam("password", loginRequest.getPassword())
                .build()
                .encode()
                .toString()
                .substring(1);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(KEYCLOAK_URL, HttpMethod.POST, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("✅ User Logged In: " + loginRequest.getEmail());
            System.out.println("🔑 Access Token: " + response.getBody().get("access_token"));
        }

        return response;
    }

    // 🔹 2️⃣ Register User (With Role Assignment)
    // 🔹 Register User (No user token required, uses admin token)
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

    // 🔹 4️⃣ Assign Role to User
    private void assignRoleToUser(String userId, String roleName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAdminToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // ✅ Get Role ID
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String roleUrl = KEYCLOAK_ROLE_URL + "/" + roleName;
        ResponseEntity<Map> roleResponse = restTemplate.exchange(roleUrl, HttpMethod.GET, requestEntity, Map.class);
        String roleId = (String) roleResponse.getBody().get("id");

        // ✅ Assign Role to User
        List<Map<String, Object>> roles = new ArrayList<>();
        Map<String, Object> role = new HashMap<>();
        role.put("id", roleId);
        role.put("name", roleName);
        roles.add(role);

        HttpEntity<List<Map<String, Object>>> assignRoleRequest = new HttpEntity<>(roles, headers);
        String assignRoleUrl = KEYCLOAK_ADMIN_URL + "/" + userId + "/role-mappings/realm";

        restTemplate.exchange(assignRoleUrl, HttpMethod.POST, assignRoleRequest, String.class);
    }

    // 🔹 5️⃣ Get Admin Token (Client Credentials Flow)
    private String getAdminToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = UriComponentsBuilder.newInstance()
                .queryParam("client_id", CLIENT_ID)
                .queryParam("client_secret", CLIENT_SECRET)
                .queryParam("grant_type", "client_credentials")
                .build()
                .encode()
                .toString()
                .substring(1);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(KEYCLOAK_URL, HttpMethod.POST, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String token = response.getBody().get("access_token").toString();
            System.out.println("🔑 Fetched Admin Token: " + token); // 🔍 Print Token for Debugging
            return token;
        } else {
            throw new RuntimeException("❌ Failed to get admin token: " + response.getBody());
        }
    }





}
