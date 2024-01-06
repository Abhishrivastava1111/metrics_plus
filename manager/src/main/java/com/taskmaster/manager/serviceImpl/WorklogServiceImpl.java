package com.taskmaster.manager.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.taskmaster.manager.dto.WorklogDto;
import com.taskmaster.manager.entity.Project;
import com.taskmaster.manager.entity.Worklog;
import com.taskmaster.manager.repository.WorklogRepository;
import com.taskmaster.manager.service.WorklogService;

@Service
public class WorklogServiceImpl implements WorklogService {

    @Autowired
    private WorklogRepository worklogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<WorklogDto> getAllWorklog() {
        List<Worklog> allPersistedWorkLog = worklogRepository.findAll();
        List<WorklogDto> dtoList = new ArrayList<>();
        for (Worklog worklog : allPersistedWorkLog) {
            WorklogDto mappedDto = modelMapper.map(worklog, WorklogDto.class);
            dtoList.add(mappedDto);
        }
        return dtoList;
    }

    @Override
    public String addWorklog(WorklogDto worklogDto) {
        // mapped the dto with the entity
        Worklog newWorklog = modelMapper.map(worklogDto, Worklog.class);

        // calling the project service for gettting the porject id
        Project project = restTemplate.getForObject("http://project-service/project/{projectId}", Project.class,
                worklogDto.getProjentId());
        // now setting the
        newWorklog.setProject(project);
        Worklog persistedWorklog = worklogRepository.save(newWorklog);
        if (persistedWorklog != null) {
            return "User saved successfully";
        } else
            return "Something went wrong";
    }

    @Override
    public ResponseEntity<String> editWorklog(WorklogDto worklogDto, Long id) {

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
