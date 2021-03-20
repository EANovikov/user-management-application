package com.xevgnov.UserManagementApplication.service;

import com.xevgnov.UserManagementApplication.dto.UserDto;
import com.xevgnov.UserManagementApplication.model.User;

import java.util.List;

public interface UserService {

  UserDto createUser(UserDto user);

  UserDto updateUser(UserDto user, Long id);

  List<UserDto> getUsers();

  User findUserByUsername(String username);

  User findUserById(Long id);

  void deleteUser(Long id);
}
