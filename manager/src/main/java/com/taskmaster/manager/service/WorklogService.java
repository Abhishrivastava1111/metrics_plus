package com.taskmaster.manager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.taskmaster.manager.dto.WorklogDto;

public interface WorklogService {

    List<WorklogDto> getAllWorklog();

    String addWorklog(WorklogDto worklogDto);

    ResponseEntity<String> editWorklog(WorklogDto entity, Long id);

}
