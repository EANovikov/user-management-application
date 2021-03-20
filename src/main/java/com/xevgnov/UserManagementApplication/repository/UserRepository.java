package com.xevgnov.UserManagementApplication.repository;

import com.xevgnov.UserManagementApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

}
