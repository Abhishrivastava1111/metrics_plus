package com.taskmaster.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.manager.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}