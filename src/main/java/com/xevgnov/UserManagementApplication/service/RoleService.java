package com.xevgnov.UserManagementApplication.service;

import com.xevgnov.UserManagementApplication.model.Role;

import java.util.List;

public interface RoleService {

  Role createRole(Role role);

  Role updateRole(Role role, Long id);

  List<Role> getRoles();

  Role findRoleByName(String name);

  Role findRoleById(Long id);

  void deleteRole(Long id);
}
