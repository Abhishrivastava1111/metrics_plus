package com.taskmaster.manager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.manager.dto.ReportRequestDto;
import com.taskmaster.manager.service.ReportService;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportRequestDto>> getAllReports() {
        List<ReportRequestDto> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportRequestDto> getReportById(@PathVariable Long id) {
        ReportRequestDto report = reportService.getReportById(id);
        if (report != null) {
            return ResponseEntity.ok(report);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<ReportRequestDto>> getReportsForProject(@PathVariable Long projectId) {
        List<ReportRequestDto> reports = reportService.getReportsForProject(projectId);
        return ResponseEntity.ok(reports);
    }
    // @PostMapping
    // public ResponseEntity<ReportDTO> createReport(@Valid @RequestBody ReportDTO
    // reportDTO) {
    // ReportDTO createdReport = reportService.createReport(reportDTO);
    // return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    // }
    // @PutMapping("/{id}")
    // public ResponseEntity<ReportDTO> updateReport(@PathVariable Long id, @Valid
    // @RequestBody ReportDTO reportDTO) {
    // ReportDTO updatedReport = reportService.updateReport(id, reportDTO);
    // if (updatedReport != null) {
    // return ResponseEntity.ok(updatedReport);
    // }
    // return ResponseEntity.notFound().build();
    // }
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
    // reportService.deleteReport(id);
    // return ResponseEntity.noContent().build();
    // }
}