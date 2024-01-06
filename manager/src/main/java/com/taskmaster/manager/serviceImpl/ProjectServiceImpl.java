package com.taskmaster.manager.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskmaster.manager.dto.ProjectRequestDto;
import com.taskmaster.manager.entity.Project;
import com.taskmaster.manager.entity.User;
import com.taskmaster.manager.entity.UserProject;
import com.taskmaster.manager.exception.UserNotFoundException;
import com.taskmaster.manager.repository.ProjectRepository;
import com.taskmaster.manager.repository.UserProjectRepository;
import com.taskmaster.manager.repository.UserRepository;
import com.taskmaster.manager.service.ProjectService;

import jakarta.transaction.Transactional;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Transactional
    public ResponseEntity<Project> createProject(ProjectRequestDto projectDto) {
        Project mappedProject = mapper.map(projectDto, Project.class);
        //////////////////////
        /*
         * we have to call RestTemplate here for user service
         * 
         */
        Optional<User> foundUser = userRepository.findById(projectDto.getUserId());
        if (foundUser.isPresent()) {
            mappedProject = projectRepository.save(mappedProject);
            UserProject userProj = userProjectRepository.save(new UserProject(mappedProject, foundUser.get()));
            Set<UserProject> projList = new HashSet<>();
            projList.add(userProj);
            mappedProject.setUserProjects(projList);
            /////////////////////
            Project savedProject = projectRepository.save(mappedProject);
            return ResponseEntity.ok(savedProject);
        } else
            throw new UserNotFoundException("the user is not found ");
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