package com.taskmaster.manager.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskmaster.manager.dto.ProjectResponseDto;
import com.taskmaster.manager.dto.UserResponse;
import com.taskmaster.manager.dto.report.ProjectReportResponseDto;
import com.taskmaster.manager.dto.report.ReportResponseDto;
import com.taskmaster.manager.service.ProjectService;
import com.taskmaster.manager.service.ReportService;
import com.taskmaster.manager.service.TaskService;
import com.taskmaster.manager.service.UserService;
import com.taskmaster.manager.service.WorklogService;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private WorklogService worklogService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<ReportResponseDto> getUserRelatedReport() {
        List<UserResponse> listOfUsers = userService.listOfUsers();

        List<UserResponse> collect = listOfUsers.stream().filter(l -> l.getStatus().equals("undeployed"))
                .collect(Collectors.toList());

        int undepCount = collect.size();
        int depCount = (listOfUsers.size()) - undepCount;

        return ResponseEntity.ok(new ReportResponseDto(undepCount, depCount));

    }

    @Override
    public ResponseEntity<ProjectReportResponseDto> getProjectReport() {

        List<ProjectResponseDto> allProjects = projectService.getAllProjects();
        List<ProjectResponseDto> completedPorjects = allProjects.stream().filter(p -> p.isCompleted())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ProjectReportResponseDto(completedPorjects.size(), allProjects.size() - completedPorjects.size()));
    }

}