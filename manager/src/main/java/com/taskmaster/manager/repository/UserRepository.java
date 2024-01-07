package com.taskmaster.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.manager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

    List<User> findByEmail(String email);

}