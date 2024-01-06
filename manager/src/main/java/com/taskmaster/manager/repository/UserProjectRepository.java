package com.taskmaster.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.manager.entity.UserProject;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

}
