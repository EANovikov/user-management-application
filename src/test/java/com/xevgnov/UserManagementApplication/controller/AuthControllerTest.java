package com.xevgnov.UserManagementApplication.controller;

import com.xevgnov.UserManagementApplication.dto.AuthenticationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest extends BaseControllerTest {

  private String loginUri;

  @BeforeEach
  public void setUp() {
    loginUri = BASE_URL + port + "/login";
  }

  @Test
  public void login_validUserAndPassword_returnsToken() throws Exception {

    // Given
    AuthenticationRequestDto authenticationRequestDto =
        new AuthenticationRequestDto("admin", "admin");

    mockMvc

        // When
        .perform(
            post(loginUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequestDto)))

        // Then
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty());
  }

  @Test
  public void login_validUserAndInvalidPassword_returns403() throws Exception {

    // Given
    AuthenticationRequestDto authenticationRequestDto =
        new AuthenticationRequestDto("admin", "123");

    mockMvc

        // When
        .perform(
            post(loginUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequestDto)))

        // Then
        .andExpect(status().isForbidden());
  }

  @Test
  public void login_notExistingUser_returns403() throws Exception {

    // Given
    AuthenticationRequestDto authenticationRequestDto =
        new AuthenticationRequestDto("no-such-user", "123");

    mockMvc

        // When
        .perform(
            post(loginUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequestDto)))

        // Then
        .andExpect(status().isForbidden());
  }
}
