package com.taskmaster.manager.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskmaster.manager.dto.ProjectRequestDto;
import com.taskmaster.manager.dto.ProjectResponseDto;
import com.taskmaster.manager.dto.UserResponse;
import com.taskmaster.manager.entity.Project;
import com.taskmaster.manager.entity.User;
import com.taskmaster.manager.entity.UserProject;
import com.taskmaster.manager.exception.ProjectNotFoundException;
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

    @Transactional
    @Override
    public List<ProjectResponseDto> getAllProjects() {
        List<Project> projectList = projectRepository.findAll();
        List<ProjectResponseDto> projectListDto = new ArrayList<>();
        for (Project project : projectList) {
            ProjectResponseDto proj = new ProjectResponseDto();
            proj.setName(project.getName());
            proj.setStartDate(project.getStartDate());
            proj.setEndDate(project.getStartDate());
            proj.setDescription(project.getDescription());
            List<UserResponse> userList = new ArrayList<>();
            for (UserProject user : project.getUserProjects()) {
                UserResponse userResponse = mapper.map(user.getUser(), UserResponse.class);
                user.getUser().getUserRoles().stream().forEach((e) -> userResponse.setRole(e.getRole().getName()));
                userList.add(userResponse);
            }
            proj.setMembers(userList);
            projectListDto.add(proj);
        }
        return projectListDto;
    }

    @Override
    public ProjectResponseDto getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        ProjectResponseDto proj = new ProjectResponseDto();
        proj.setName(project.getName());
        proj.setStartDate(project.getStartDate());
        proj.setEndDate(project.getStartDate());
        proj.setDescription(project.getDescription());
        List<UserResponse> userList = new ArrayList<>();
        for (UserProject user : project.getUserProjects()) {
            UserResponse userResponse = mapper.map(user.getUser(), UserResponse.class);
            user.getUser().getUserRoles().stream().forEach((e) -> userResponse.setRole(e.getRole().getName()));
            userList.add(userResponse);
        }
        proj.setMembers(userList);
        return proj;
    }

    @Transactional
    public ResponseEntity<Project> createProject(ProjectRequestDto projectDto) {
        Project mappedProject = new Project();
        mappedProject.setDescription(projectDto.getDescription());
        mappedProject.setEndDate(projectDto.getEndDate());
        mappedProject.setStartDate(projectDto.getStartDate());
        mappedProject.setName(projectDto.getName());
        Optional<User> foundUser = userRepository.findById(projectDto.getUserId());

        if (foundUser.isPresent()) {
            // Save the mapped project first
            mappedProject = projectRepository.save(mappedProject);

            // Create and save the UserProject
            UserProject userProj = userProjectRepository.save(new UserProject(mappedProject, foundUser.get()));
            Set<UserProject> projList = new HashSet<>();
            projList.add(userProj);
            mappedProject.setUserProjects(projList);

            // No need to save the project again
            return ResponseEntity.ok(mappedProject);
        } else {
            throw new UserNotFoundException("the user is not found ");
        }
    }

    @Transactional
    @Override
    public String deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        project.setCompleted(true);
        return "Successfully deleted";
    }

    @Transactional
    @Override
    public ResponseEntity<Project> updateProject(Long projectId, ProjectRequestDto updatedProject) {
        Project persistedProject = projectRepository.findById(projectId).orElseGet(null);
        if (persistedProject != null) {

            persistedProject.setDescription(updatedProject.getDescription());
            persistedProject.setEndDate(updatedProject.getEndDate());
            persistedProject.setStartDate(updatedProject.getStartDate());
            persistedProject.setName(updatedProject.getName());
            return ResponseEntity.ok(persistedProject);
        } else {
            throw new UserNotFoundException("No project found with this id");
        }
    }
}