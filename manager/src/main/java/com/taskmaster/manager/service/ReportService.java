package com.taskmaster.manager.service;

import org.springframework.http.ResponseEntity;

import com.taskmaster.manager.dto.report.ProjectReportResponseDto;
import com.taskmaster.manager.dto.report.ReportResponseDto;

public interface ReportService {

    ResponseEntity<ReportResponseDto> getUserRelatedReport();

    ResponseEntity<ProjectReportResponseDto> getProjectReport();
}