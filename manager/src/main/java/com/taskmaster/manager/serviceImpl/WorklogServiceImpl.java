package com.taskmaster.manager.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.taskmaster.manager.dto.WorklogDto;
import com.taskmaster.manager.dto.WorklogEditDto;
import com.taskmaster.manager.entity.Project;
import com.taskmaster.manager.entity.Task;
import com.taskmaster.manager.entity.User;
import com.taskmaster.manager.entity.Worklog;
import com.taskmaster.manager.exception.ProjectNotFoundException;
import com.taskmaster.manager.exception.TaskNotFoundException;
import com.taskmaster.manager.exception.UserNotFoundException;
import com.taskmaster.manager.exception.WorklogNotFoundException;
import com.taskmaster.manager.repository.ProjectRepository;
import com.taskmaster.manager.repository.TaskRepository;
import com.taskmaster.manager.repository.UserRepository;
import com.taskmaster.manager.repository.WorklogRepository;
import com.taskmaster.manager.service.WorklogService;

import jakarta.transaction.Transactional;

@Service
public class WorklogServiceImpl implements WorklogService {

    @Autowired
    private WorklogRepository worklogRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    @Override
    public ResponseEntity<WorklogDto> getWorklogById(Long id) {
        Worklog worklog = worklogRepository.findById(id)
                .orElseThrow(() -> new WorklogNotFoundException("worklog with the given id does not exist"));
        WorklogDto mappedWorklog = modelMapper.map(worklog, WorklogDto.class);
        Long[] taskIds = worklog.getTasks().stream().map(Task::getId).toArray(Long[]::new);
        mappedWorklog.setTaskIds(taskIds);

        return ResponseEntity.ok(mappedWorklog);

    }

    @Transactional
    @Override
    public List<WorklogDto> getAllWorklog() {
        List<Worklog> allPersistedWorkLog = worklogRepository.findAll();
        List<WorklogDto> dtoList = new ArrayList<>();

        for (Worklog worklog : allPersistedWorkLog) {
            WorklogDto mappedDto = modelMapper.map(worklog, WorklogDto.class);
            mappedDto.setProjentId(worklog.getProject().getId());

            // Extracting task IDs and setting them in the WorklogDto as an array
            Long[] taskIds = worklog.getTasks().stream().map(Task::getId).toArray(Long[]::new);
            mappedDto.setTaskIds(taskIds);

            dtoList.add(mappedDto);
        }

        return dtoList;
    }

    @Transactional
    @Override
    public String addWorklog(WorklogDto worklogDto) {
        // Map the DTO to the entity
        Worklog newWorklog = new Worklog();
        newWorklog.setDescription(worklogDto.getDescription());
        newWorklog.setName(worklogDto.getName());

        // Initialize the set of tasks
        Set<Task> setOfTasks = new HashSet<>();

        for (Long taskId : worklogDto.getTaskIds()) {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found"));
            task.setCompleted(true);
            task.setWorklog(newWorklog);
            setOfTasks.add(task);
        }

        User user = userRepository.findById(worklogDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + worklogDto.getUserId() + " not found"));

        Project project = projectRepository.findById(worklogDto.getProjentId())
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project with ID " + worklogDto.getProjentId() + " not found"));

        newWorklog.setTasks(setOfTasks);
        newWorklog.setProject(project);
        newWorklog.setUser(user);

        newWorklog = worklogRepository.save(newWorklog);

        if (newWorklog != null) {
            return "Log saved successfully";
        } else {
            return "Something went wrong";
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> editWorklog(WorklogEditDto worklogDto, Long id) {

        Optional<Worklog> persistedLog = worklogRepository.findById(id);
        if (!persistedLog.isPresent()) {
            return ResponseEntity.status(400).body("worklog not found");
        } else {
            Worklog persistedWorklog = persistedLog.get();

            persistedWorklog.setName(worklogDto.getName());
            persistedWorklog.setDescription(worklogDto.getDescription());

            return ResponseEntity.ok("Edited successfully");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteWorklog(Long id) {
        Optional<Worklog> persistedLog = worklogRepository.findById(id);
        if (!persistedLog.isPresent()) {
            return ResponseEntity.status(400).body("worklog not found");
        } else {
            Worklog persistedWorklog = persistedLog.get();

            worklogRepository.delete(persistedWorklog);

            return ResponseEntity.ok("Deleted successfully");
        }
    }

}
