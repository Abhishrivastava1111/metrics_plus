package com.taskmaster.manager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.taskmaster.manager.dto.ProjectRequestDto;
import com.taskmaster.manager.dto.ProjectResponseDto;
import com.taskmaster.manager.entity.Project;

public interface ProjectService {

    List<ProjectResponseDto> getAllProjects();

    ProjectResponseDto getProjectById(Long projectId);

    ResponseEntity<Project> createProject(ProjectRequestDto projectRequestDto);

    ResponseEntity<Project> updateProject(Long projectId, ProjectRequestDto updatedProject);

    String deleteProject(Long projectId);
}
