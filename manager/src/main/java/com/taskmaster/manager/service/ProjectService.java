package com.taskmaster.manager.service;

import java.util.List;
import java.util.Optional;

import com.taskmaster.manager.dto.ProjectRequestDto;
import com.taskmaster.manager.entity.Project;

public interface ProjectService {
    List<Project> getAllProjects();

    Optional<Project> getProjectById(Long projectId);

    Project createProject(ProjectRequestDto projectRequestDto);

    Project updateProject(Long projectId, Project updatedProject);

    void deleteProject(Long projectId);
}
