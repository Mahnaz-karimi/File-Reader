package com.jetbrains.intellij;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jetbrains.intellij.controller.StringLengthController;
import com.jetbrains.intellij.controller.UserController;
import com.jetbrains.intellij.entity.LengthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StringLengthController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@Import(TestSecurityConfig.class)
public class StringLengthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testValidLengthRequest() throws Exception {
        LengthRequest request = new LengthRequest();
        request.setStrings(List.of("Foerste element", "Andet element", "Tredje element"));
        request.setMaxLength(100);

        mockMvc.perform(post("/string-check/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allowed").value(true))
                .andExpect(jsonPath("$.message").value("Tilladt: samlet længde er inden for grænsen."));
    }

    @Test
    public void testExceedingLengthRequest() throws Exception {
        LengthRequest request = new LengthRequest();
        request.setStrings(List.of("Foerste element", "Andet element", "Tredje element"));
        request.setMaxLength(10); // Set a maxLength that will be exceeded

        mockMvc.perform(post("/string-check/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allowed").value(false))
                .andExpect(jsonPath("$.message").value("Samlet længde overstiger maxLength."));
    }
}
