package com.taskmaster.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taskmaster.manager.dto.ProjectRequestDto;
import com.taskmaster.manager.entity.Project;
import com.taskmaster.manager.service.ProjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectServiceImpl;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectServiceImpl.getAllProjects();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
        Optional<Project> project = projectServiceImpl.getProjectById(projectId);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectRequestDto projectRequestDto) {
        Project newProject = new Project(); // You may use a mapper to convert DTO to entity
        // set properties from projectDto to newProject
        Project createdProject = projectServiceImpl.createProject(newProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId,
            @RequestBody ProjectRequestDto projectRequestDto) {
        Optional<Project> existingProject = projectServiceImpl.getProjectById(projectId);
        if (existingProject.isPresent()) {
            Project updatedProject = new Project(); // You may use a mapper to convert DTO to entity
            // set properties from projectDto to updatedProject
            Project savedProject = projectServiceImpl.updateProject(projectId, updatedProject);
            return ResponseEntity.ok(savedProject);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectServiceImpl.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}