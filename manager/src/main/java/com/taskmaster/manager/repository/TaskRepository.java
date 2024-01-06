package com.taskmaster.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.manager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
