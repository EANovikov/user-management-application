package com.xevgnov.UserManagementApplication.service;

import com.xevgnov.UserManagementApplication.exception.IllegalRoleException;
import com.xevgnov.UserManagementApplication.exception.RoleNotFoundException;
import com.xevgnov.UserManagementApplication.model.Role;
import com.xevgnov.UserManagementApplication.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

  private final RolesRepository rolesRepository;
  private final boolean adminUser;

  @Autowired
  public RoleServiceImpl(
      RolesRepository rolesRepository, @Qualifier("adminUser") boolean adminUser) {
    this.rolesRepository = rolesRepository;
    this.adminUser = adminUser;
  }

  @Override
  public Role createRole(Role role) {
    Role roleFromDb = rolesRepository.save(role);
    log.info("Role [" + roleFromDb + "] has been saved");
    return roleFromDb;
  }

  @Override
  public Role updateRole(Role role, Long id) {
    Role roleFromDb = findRoleById(id);
    roleFromDb.setName(role.getName());
    roleFromDb.setPermissions(role.getPermissions());
    log.info("Role [" + roleFromDb + "] has been updated");
    return rolesRepository.save(roleFromDb);
  }

  @Override
  public List<Role> getRoles() {
    List<Role> rolesFromDb = rolesRepository.findAll();
    log.info("Found roles " + rolesFromDb);
    return rolesFromDb;
  }

  @Override
  public Role findRoleByName(String name) {
    if (ObjectUtils.isEmpty(name)) {
      throw new IllegalRoleException("No role has been defined");
    }
    Role roleFromDb = rolesRepository.findByName(name);
    if (roleFromDb == null) {
      throw new IllegalRoleException("The role with [" + name + "] has not been found");
    }
    if (!adminUser && name.equals("ROLE_ADMIN")) {
      throw new IllegalRoleException("The role [" + name + "] can be set by admin only");
    }
    log.info("Found role " + roleFromDb);
    return roleFromDb;
  }

  @Override
  public Role findRoleById(Long id) {
    Role roleFromDb = rolesRepository.findById(id).orElseThrow(RoleNotFoundException::new);
    log.info("Found role " + roleFromDb);
    return roleFromDb;
  }

  @Override
  public void deleteRole(Long id) {
    Role roleFromDb = findRoleById(id);
    rolesRepository.delete(roleFromDb);
    log.info("Role with id " + id + " has been removed");
  }
}
