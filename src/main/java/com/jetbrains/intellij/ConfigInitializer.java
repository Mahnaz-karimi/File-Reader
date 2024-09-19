package com.jetbrains.intellij;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;

public class ConfigInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            // Indlæs config.json
            ObjectMapper objectMapper = new ObjectMapper();
            String configFilePath = System.getProperty("config.file.path", "config.json");
            Resource resource = new ClassPathResource(configFilePath);
            Map<String, String> configMap = objectMapper.readValue(resource.getInputStream(), Map.class);

            System.setProperty("spring.datasource.url", configMap.get("spring.datasource.url"));
            System.setProperty("spring.datasource.username", configMap.get("spring.datasource.username"));
            System.setProperty("spring.datasource.password", configMap.get("spring.datasource.password"));

            System.setProperty("POSTGRES_DB", configMap.get("postgres.db"));
            System.setProperty("POSTGRES_USER", configMap.get("postgres.user"));
            System.setProperty("POSTGRES_PASSWORD", configMap.get("postgres.password"));

            System.setProperty("url", configMap.get("url"));
            System.setProperty("username", configMap.get("username"));
            System.setProperty("password", configMap.get("password"));

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.json", e);
        }
    }
}

