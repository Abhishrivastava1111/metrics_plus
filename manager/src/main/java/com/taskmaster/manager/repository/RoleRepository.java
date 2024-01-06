package com.taskmaster.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.manager.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role);

}