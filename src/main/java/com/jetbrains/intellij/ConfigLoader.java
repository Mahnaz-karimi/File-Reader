package com.jetbrains.intellij;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Configuration
public class ConfigLoader {

    @Value("${config.file.path:classpath:config.json}")
    private String configFilePath;

    @PostConstruct
    public Map<String, String> loadConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource(configFilePath);
        if (!resource.exists()) {
            throw new FileNotFoundException("Config file not found at: " + configFilePath);
        }
        Map<String, String> configMap = objectMapper.readValue(resource.getInputStream(), Map.class);

        // Load environment variables
        System.setProperty("spring.datasource.url", configMap.get("spring.datasource.url"));
        System.setProperty("spring.datasource.username", configMap.get("spring.datasource.username"));
        System.setProperty("spring.datasource.password", configMap.get("spring.datasource.password"));

        System.setProperty("POSTGRES_DB", configMap.get("postgres.db"));
        System.setProperty("POSTGRES_USER", configMap.get("postgres.user"));
        System.setProperty("POSTGRES_PASSWORD", configMap.get("postgres.password"));

        System.setProperty("url", configMap.get("url"));
        System.setProperty("username", configMap.get("username"));
        System.setProperty("password", configMap.get("password"));

        System.out.println("Loaded config: " + configMap);

        return configMap;  // Returnér bean (map af konfigurationsdata)
    }
}
