package com.xevgnov.UserManagementApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {

  @NotBlank(message = "Empty username")
  private String username;

  @NotBlank(message = "Empty password")
  private String password;
}
