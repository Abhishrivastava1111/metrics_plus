package com.taskmaster.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.manager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

}