package com.xevgnov.UserManagementApplication.controller;

import com.xevgnov.UserManagementApplication.model.Role;
import com.xevgnov.UserManagementApplication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xevgnov.UserManagementApplication.security.UserSecurityRole.ROLE_ADMIN;

@RestController
@RequestMapping("/roles")
@Secured(ROLE_ADMIN)
public class RoleController {

  private RoleService roleService;

  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @GetMapping
  public List<Role> listRoles() {
    return roleService.getRoles();
  }

  @PostMapping
  public Role createRole(@Validated @RequestBody Role role) {
    return roleService.createRole(role);
  }

  @PutMapping("/{id}")
  public Role updateRole(@Validated @RequestBody Role role, @Validated @PathVariable Long id) {
    return roleService.updateRole(role, id);
  }

  @DeleteMapping("/{id}")
  public void deleteRole(@Validated @PathVariable Long id) {
    roleService.deleteRole(id);
  }
}
