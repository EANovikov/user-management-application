package com.xevgnov.UserManagementApplication.controller;

import com.xevgnov.UserManagementApplication.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends BaseControllerTest {

  private String usersUri;
  private String token;

  @BeforeEach
  public void setUp() {
    usersUri = BASE_URL + port + "/users";
  }

  @Test
  public void listUsers_adminUser_getSuccessfully() throws Exception {

    // Given
    token = getToken("admin", "admin");
    UserDto[] users = {
      new UserDto(1L, "admin", "ROLE_ADMIN"),
      new UserDto(2L, "user", "ROLE_USER"),
      new UserDto(3L, "qa", "QA")
    };

    mockMvc

        // When
        .perform(
            get(usersUri)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))

        // Then
        .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(users)));
  }
}
