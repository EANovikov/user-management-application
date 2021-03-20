package com.xevgnov.UserManagementApplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xevgnov.UserManagementApplication.dto.AuthenticationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class BaseControllerTest {
  @Value("${server.port}")
  String port;

  @Autowired ObjectMapper objectMapper;

  @Autowired MockMvc mockMvc;

  public static final String BASE_URL = "http://localhost:";

  String getToken(String username, String password) throws Exception {
    String token =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(BASE_URL + port + "/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            new AuthenticationRequestDto(username, password))))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8)
            .replace("{\"token\":\"", "")
            .replace("\"}", "");
    return "Bearer " + token;
  }
}
