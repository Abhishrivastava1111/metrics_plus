package com.taskmaster.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taskmaster.manager.dto.ProjectRequestDto;
import com.taskmaster.manager.dto.ProjectResponseDto;
import com.taskmaster.manager.entity.Project;
import com.taskmaster.manager.service.ProjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<ProjectResponseDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @PostMapping("/")
    public ResponseEntity<Project> createProject(@RequestBody ProjectRequestDto projectRequestDto) {

        return projectService.createProject(projectRequestDto);

    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId,
            @RequestBody ProjectRequestDto projectRequestDto) {
        return projectService.updateProject(projectId, projectRequestDto);

    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}