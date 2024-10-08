package com.jetbrains.intellij;

import com.jetbrains.intellij.controller.UserController;
import com.jetbrains.intellij.entity.AppUser;
import com.jetbrains.intellij.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.security.test.context.support.WithMockUser;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@Import(TestSecurityConfig.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private AppUser appUser;

    @BeforeEach
    public void setup() {
        appUser = new AppUser();
        appUser.setId(1L);
        appUser.setUsername("testuser");
        appUser.setPassword("password123");
    }

    @Test
    @WithMockUser // Simulerer en bruger
    public void testCreateUser() throws Exception {
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");

        appUser.setPassword("encodedPassword");

        Mockito.when(userRepository.save(Mockito.any(AppUser.class))).thenReturn(appUser);

        String userJson = "{\"username\":\"testuser\", \"password\":\"password123\"}";

        mockMvc.perform(post("/req/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.username", is("testuser")))
                        .andExpect(jsonPath("$.password", is("encodedPassword")))
                        .andExpect(jsonPath("$.password", not("password123")));

    }
}
