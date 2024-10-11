package com.jetbrains.intellij;

import com.jetbrains.intellij.controller.StringLengthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StringLengthController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@Import(TestSecurityConfig.class)
public class StringLengthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testStringCheckPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/string-check/string-check.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("string-check")); // Kontroller at view'et har korrekt navn
    }

    @Test
    public void testSumOfSubstringsLength_ValidLength() throws Exception {
        mockMvc.perform(post("/string-check/check")
                        .param("strings", "first,second,third")
                        .param("maxLength", "50"))
                .andExpect(status().isOk())
                .andExpect(view().name("result")) // Kontroller view-navnet
                .andExpect(model().attribute("result", "Tilladt: samlet længde er inden for grænsen."));
    }

    @Test
    public void testSumOfSubstringsLength_ExceedsLength() throws Exception {
        mockMvc.perform(post("/string-check/check")
                        .param("strings", "first,second,third,veryverylongstring")
                        .param("maxLength", "20"))
                .andExpect(status().isOk())
                .andExpect(view().name("result")) // Kontroller view-navnet
                .andExpect(model().attribute("result", "Samlet længde overstiger maxLength."));
    }
}
