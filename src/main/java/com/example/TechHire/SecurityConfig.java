package com.example.TechHire;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/candidate/dashboard").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.GET, "/api/auth/hr/dashboard").hasRole("HR")
                        .requestMatchers(HttpMethod.GET, "/api/auth/profile").hasAnyRole("HR", "CANDIDATE")
                        .requestMatchers(HttpMethod.GET, "/api/auth/admin").hasRole("ADMIN")

                        // Allow public access to job postings
                        .requestMatchers(HttpMethod.GET, "/api/jobs_posting/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/jobs_posting").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/jobs_posting/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/jobs_posting/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/job_applications/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/job_applications/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/job_applications/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/assessments/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/assessments").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/assessments/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/assessments/**").permitAll()





                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
