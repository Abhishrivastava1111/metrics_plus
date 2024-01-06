package com.taskmaster.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.manager.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
