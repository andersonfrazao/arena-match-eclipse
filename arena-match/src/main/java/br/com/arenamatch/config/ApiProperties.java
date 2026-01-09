package br.com.arenamatch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiProperties {

    @Value("${arenamatch.api.base-url}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}
