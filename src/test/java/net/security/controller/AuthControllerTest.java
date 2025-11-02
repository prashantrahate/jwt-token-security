package net.security.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.security.model.AppUser;
import net.security.model.LoginRequest;
import net.security.model.Token;
import net.security.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private AuthService authService;

  @Test
  void registerUser_shouldReturnSavedUser() throws Exception {
    AppUser appUser = new AppUser("user1@example.com", "user1", "12345", "ROLE_USER");
    AppUser createdUser = new AppUser("user1@example.com", "user1", "12345", "ROLE_USER");
    createdUser.setId(1L);
    createdUser.setCreatedBy("system");

    when(authService.registerUser(appUser)).thenReturn(createdUser);

    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appUser)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void loginUser_returnGeneratedToken() throws Exception {
    LoginRequest loginRequest = new LoginRequest("user1", "12345");
    Token generatedToken = new Token("token", "Bearer", 3600L, "user1", null);

    when(authService.generateToken(loginRequest)).thenReturn(generatedToken);

    mockMvc
        .perform(
            post("/api/v1/auth/token")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("username=user1&password=12345"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.token").value("token"));
  }
}
