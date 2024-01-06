package com.taskmaster.manager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.taskmaster.manager.dto.WorklogDto;
import com.taskmaster.manager.dto.WorklogEditDto;

public interface WorklogService {

    List<WorklogDto> getAllWorklog();

    String addWorklog(WorklogDto worklogDto);

    ResponseEntity<String> editWorklog(WorklogEditDto entity, Long id);

    ResponseEntity<String> deleteWorklog(Long id);

    ResponseEntity<WorklogDto> getWorklogById(Long id);

}
