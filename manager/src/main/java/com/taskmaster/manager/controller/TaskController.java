package com.taskmaster.manager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.manager.dto.TaskDto;
import com.taskmaster.manager.service.TaskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public ResponseEntity<List<TaskDto>> getTasks() {
        ResponseEntity<List<TaskDto>> list = taskService.getAllTasks();
        if (list != null) {
            return list;
        }
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/")
    public ResponseEntity<String> addTask(@RequestBody TaskDto entity) {
        return taskService.addNewTask(entity);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editTaskEntity(@PathVariable Long id, @RequestBody TaskDto dto) {
        return taskService.editTask(id, dto);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

}
