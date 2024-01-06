package com.taskmaster.manager.service;

import java.util.List;

import com.taskmaster.manager.dto.ReportRequestDto;

public interface ReportService {
    List<ReportRequestDto> getAllReports();

    ReportRequestDto getReportById(Long id);

    List<ReportRequestDto> getReportsForProject(Long projectId);
}