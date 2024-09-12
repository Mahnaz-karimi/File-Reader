package com.jetbrains.intellij.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;

@Configuration
public class AppConfig {
    @Value("${config.file.path}")
    private String configFilePath;
    @Bean
    public Config config() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Indlæs JSON-filen fra src/main/resources/config.json
        Resource resource = new ClassPathResource(configFilePath);
        return objectMapper.readValue(resource.getInputStream(), Config.class);
    }
}
