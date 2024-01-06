package com.taskmaster.manager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmaster.manager.entity.Project;
import com.taskmaster.manager.repository.ProjectRepository;
import com.taskmaster.manager.service.ProjectService;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long projectId, Project updatedProject) {
        if (projectRepository.existsById(projectId)) {
            updatedProject.setId(projectId);
            return projectRepository.save(updatedProject);
        }
        return null; // Handle not found scenario
    }

    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}