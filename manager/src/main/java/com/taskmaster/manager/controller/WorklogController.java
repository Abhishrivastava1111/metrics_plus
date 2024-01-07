package com.taskmaster.manager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import com.taskmaster.manager.dto.WorklogDto;
import com.taskmaster.manager.dto.WorklogEditDto;
import com.taskmaster.manager.service.WorklogService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/v1/worklog")
public class WorklogController {
    @Autowired
    private WorklogService worklogService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public ResponseEntity<List<WorklogDto>> getAllWorklog() {
        return ResponseEntity.ok(worklogService.getAllWorklog());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<WorklogDto> getWorklog(@PathVariable Long id) {
        return worklogService.getWorklogById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<String> addWorklog(@RequestBody WorklogDto worklogDto) {
        return ResponseEntity.ok(worklogService.addWorklog(worklogDto));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<String> editWorklog(@PathVariable Long id, @RequestBody WorklogEditDto entity) {
        return worklogService.editWorklog(entity, id);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorklog(@PathVariable Long id) {
        return worklogService.deleteWorklog(id);
    }
}