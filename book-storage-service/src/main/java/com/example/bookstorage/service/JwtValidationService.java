package com.example.bookstorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtValidationService {

    @Value("${auth-service.url}")
    private String authServiceUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public boolean validateToken(String token) {
        log.info("Validating token");
        String url = authServiceUrl + "/auth/remainingTime?token=" + token;
        try {
            String response = restTemplate().postForObject(url, null, String.class);
            System.out.println(12);
            if (response != null) {
                System.out.println(1);
                return !response.contains("expired");
            }
        } catch (Exception e) {
            log.warn("Validation failed");
            return false;
        }
        return true;
    }
}
