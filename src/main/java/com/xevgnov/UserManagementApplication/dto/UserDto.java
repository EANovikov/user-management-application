package com.xevgnov.UserManagementApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  @Positive private Long id;

  @NotBlank private String username;

  @NotBlank
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @NotBlank private String role;

  public UserDto(@Positive Long id, @NotBlank String username, @NotBlank String role) {
    this.id = id;
    this.username = username;
    this.role = role;
  }
}
