package com.taskmaster.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.manager.dto.report.ProjectReportResponseDto;
import com.taskmaster.manager.dto.report.ReportResponseDto;
import com.taskmaster.manager.service.ReportService;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/userDepReport")
    public ResponseEntity<ReportResponseDto> getUserReport() {
        return reportService.getUserRelatedReport();
    }

    @GetMapping("/projComReport")
    public ResponseEntity<ProjectReportResponseDto> getProjectReport() {
        return reportService.getProjectReport();
    }

}