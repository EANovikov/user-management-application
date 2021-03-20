package com.xevgnov.UserManagementApplication.controller;

import com.xevgnov.UserManagementApplication.dto.UserDto;
import com.xevgnov.UserManagementApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xevgnov.UserManagementApplication.security.UserSecurityRole.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @Secured(ROLE_LIST_USERS)
  public List<UserDto> listUsers() {
    return userService.getUsers();
  }

  @PostMapping
  @Secured(ROLE_CREATE_USERS)
  public UserDto createUser(@Validated @RequestBody UserDto user) {
    return userService.createUser(user);
  }

  @PutMapping("/{id}")
  @Secured(ROLE_EDIT_USERS)
  public UserDto updateUser(
      @Validated @RequestBody UserDto user, @Validated @PathVariable Long id) {
    return userService.updateUser(user, id);
  }

  @DeleteMapping("/{id}")
  @Secured(ROLE_DELETE_USERS)
  public void deleteUser(@Validated @PathVariable Long id) {
    userService.deleteUser(id);
  }
}
