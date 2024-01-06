package com.taskmaster.manager.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskmaster.manager.dto.TaskDto;
import com.taskmaster.manager.entity.Task;
import com.taskmaster.manager.repository.TaskRepository;
import com.taskmaster.manager.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> allTasks = taskRepository.findAll();
        List<TaskDto> list = new ArrayList<>();
        for (Task task : allTasks) {
            TaskDto mappedDto = mapper.map(task, TaskDto.class);
            list.add(mappedDto);

        }
        return list;
    }

    @Override
    public ResponseEntity<String> addNewTask(TaskDto entity) {
        Task mappedTask = mapper.map(entity, Task.class);
        Task persistedTask = taskRepository.save(mappedTask);
        if (persistedTask != null) {
            return ResponseEntity.ok("Saved Successfully");
        }
        return ResponseEntity.status(500).body("something went wrong");

    }

    @Override
    public ResponseEntity<String> editTask(Long id, TaskDto dto) {
        Optional<Task> opTask = taskRepository.findById(id);
        if (!opTask.isPresent())
            return ResponseEntity.badRequest().body("Task does't exist");
        else {
            Task mappedTask = mapper.map(dto, Task.class);
            taskRepository.save(mappedTask);
            return ResponseEntity.ok("edited successfully");
        }

    }

    @Override
    public ResponseEntity<String> deleteTask(Long id) {
        Optional<Task> opTask = taskRepository.findById(id);
        if (opTask.isPresent()) {
            taskRepository.delete(opTask.get());
            return ResponseEntity.ok("Deleted successfully");

        }
        return ResponseEntity.status(500).body("something went wrong");
    }

}
