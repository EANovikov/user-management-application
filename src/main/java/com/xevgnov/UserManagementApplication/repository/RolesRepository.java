package com.xevgnov.UserManagementApplication.repository;

import com.xevgnov.UserManagementApplication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

  Role findByName(String name);

}
