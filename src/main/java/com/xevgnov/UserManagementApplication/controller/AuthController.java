package com.xevgnov.UserManagementApplication.controller;

import com.xevgnov.UserManagementApplication.dto.AuthenticationRequestDto;
import com.xevgnov.UserManagementApplication.dto.AuthenticationResponseDto;
import com.xevgnov.UserManagementApplication.exception.UserNotFoundException;
import com.xevgnov.UserManagementApplication.model.User;
import com.xevgnov.UserManagementApplication.security.JwtTokenProvider;
import com.xevgnov.UserManagementApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;

  @Autowired
  public AuthController(
      AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> login(
      @RequestBody AuthenticationRequestDto authenticationDto) {
    try {
      String username = authenticationDto.getUsername();
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, authenticationDto.getPassword()));
      User user = userService.findUserByUsername(username);

      if (user == null) {
        throw new UserNotFoundException("User with name " + username + " does not exist!");
      }

      String token = jwtTokenProvider.createToken(user);
      return ResponseEntity.ok(new AuthenticationResponseDto(token));

    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid username or password!");
    }
  }
}
