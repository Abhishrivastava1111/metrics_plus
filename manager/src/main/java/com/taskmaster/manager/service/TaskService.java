package com.taskmaster.manager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.taskmaster.manager.dto.TaskDto;

public interface TaskService {

    ResponseEntity<List<TaskDto>> getAllTasks();

    ResponseEntity<String> addNewTask(TaskDto entity);

    ResponseEntity<String> editTask(Long id, TaskDto dto);

    ResponseEntity<String> deleteTask(Long id);

}
