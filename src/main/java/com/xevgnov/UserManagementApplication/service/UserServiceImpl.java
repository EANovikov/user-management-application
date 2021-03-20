package com.xevgnov.UserManagementApplication.service;

import com.xevgnov.UserManagementApplication.dto.UserDto;
import com.xevgnov.UserManagementApplication.exception.UserAlreadyExistsException;
import com.xevgnov.UserManagementApplication.exception.UserNotFoundException;
import com.xevgnov.UserManagementApplication.model.User;
import com.xevgnov.UserManagementApplication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleService roleService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      RoleService roleService,
      BCryptPasswordEncoder passwordEncoder,
      ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    userUniqueCheck(userDto);
    User user = modelMapper.map(userDto, User.class);
    user.setRole(roleService.findRoleByName(userDto.getRole()));
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User registeredUser = userRepository.save(user);
    userDto.setId(registeredUser.getId());
    log.info("user {} successfully created", registeredUser);
    return userDto;
  }

  @Override
  public List<UserDto> getUsers() {
    List<User> users = userRepository.findAll();
    log.info("Found {} users", users.size());
    List<UserDto> userDtos =
        users.stream()
            .map(
                u -> {
                  UserDto mappedUser = modelMapper.map(u, UserDto.class);
                  mappedUser.setRole(u.getRole().getName());
                  return mappedUser;
                })
            .collect(Collectors.toList());
    log.debug("Converted Users to UserDtos");
    return userDtos;
  }

  @Override
  public User findUserByUsername(String username) {
    User userFromDb = userRepository.findByUsername(username);
    if (userFromDb == null) {
      throw new UserNotFoundException();
    }
    log.info("Found user: {}", userFromDb);
    return userFromDb;
  }

  @Override
  public User findUserById(Long id) {
    User userFromDb = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    log.info("Found user: {}", userFromDb);
    return userFromDb;
  }

  @Override
  public void deleteUser(Long id) {
    User user = findUserById(id);
    userRepository.delete(user);
    log.info("Removed user with id: {}", id);
  }

  @Override
  public UserDto updateUser(UserDto userDto, Long id) {

    userDto.setId(id);
    User userFromDb = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    if (!userDto.getUsername().equals(userFromDb.getUsername())) {
      userUniqueCheck(userDto);
    }
    userFromDb.setRole(roleService.findRoleByName(userDto.getRole()));
    userFromDb.setUsername(userDto.getUsername());
    userFromDb.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userRepository.save(userFromDb);
    log.info("Updated user: {}", userDto);
    return userDto;
  }

  private void userUniqueCheck(UserDto user) {
    if (userRepository.findByUsername(user.getUsername()) != null) {
      throw new UserAlreadyExistsException(
          "User with name " + user.getUsername() + " is already existing!");
    }
  }
}
