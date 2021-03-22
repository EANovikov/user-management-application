package com.xevgnov.UserManagementApplication.controller;

import com.xevgnov.UserManagementApplication.model.Permission;
import com.xevgnov.UserManagementApplication.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest extends BaseControllerTest {

  private String rolesUri;
  private String token;

  @BeforeEach
  public void setUp() {
    rolesUri = BASE_URL + port + "/roles";
  }

  @Test
  public void createRole_duplicatedRoleName_internalServerError() throws Exception {
    // Given
    token = getToken("admin", "admin");
    Role duplicatedRole = new Role();
    duplicatedRole.setName("ROLE_ADMIN");
    duplicatedRole.setPermissions(Set.of(Permission.LIST_USERS));

    mockMvc

        // When
        .perform(
            post(rolesUri)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(duplicatedRole)))

        // Then
        .andExpect(status().is5xxServerError());
  }

  @Test
  public void updateRole_duplicatedRoleName_internalServerError() throws Exception {
    // Given
    token = getToken("admin", "admin");
    Role duplicatedRole = new Role();
    duplicatedRole.setName("ROLE_ADMIN");
    duplicatedRole.setPermissions(Set.of(Permission.LIST_USERS));
    assertThatThrownBy(
            () ->
                mockMvc

                    // When
                    .perform(
                    post(rolesUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(duplicatedRole))))

        // Then
        .isInstanceOf(NestedServletException.class)
        .hasMessageContaining("ConstraintViolationException: could not execute statement");
  }
}
