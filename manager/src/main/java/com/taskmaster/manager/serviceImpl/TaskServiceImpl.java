package com.taskmaster.manager.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskmaster.manager.dto.TaskDto;
import com.taskmaster.manager.entity.Task;
import com.taskmaster.manager.repository.TaskRepository;
import com.taskmaster.manager.service.TaskService;

import jakarta.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> allTasks = taskRepository.findAll();
        List<TaskDto> taskDtos = allTasks.stream()
                .map(task -> mapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

    @Override
    public ResponseEntity<String> addNewTask(TaskDto entity) {
        Task mappedTask = mapper.map(entity, Task.class);
        Task persistedTask = taskRepository.save(mappedTask);
        return persistedTask != null ? ResponseEntity.ok("Saved Successfully")
                : ResponseEntity.status(500).body("Something went wrong");
    }

    @Override
    @Transactional
    public ResponseEntity<String> editTask(Long id, TaskDto dto) {
        Optional<Task> opTask = taskRepository.findById(id);
        if (!opTask.isPresent()) {
            return ResponseEntity.badRequest().body("Task with ID " + id + " does not exist");
        } else {
            Task mappedTask = opTask.get();
            mappedTask.setDesc(dto.getDesc());
            mappedTask.setName(dto.getName());
            taskRepository.save(mappedTask);
            return ResponseEntity.ok("Edited successfully");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteTask(Long id) {
        Optional<Task> opTask = taskRepository.findById(id);
        if (opTask.isPresent()) {
            taskRepository.delete(opTask.get());
            return ResponseEntity.ok("Deleted successfully");
        }
        return ResponseEntity.status(500).body("Task with ID " + id + " not found");
    }
}
