package com.taskmaster.manager.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmaster.manager.dto.ReportRequestDto;
import com.taskmaster.manager.entity.Report;
import com.taskmaster.manager.repository.ReportRepository;
import com.taskmaster.manager.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ReportRequestDto> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportRequestDto getReportById(Long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        return optionalReport.map(this::convertToDto).orElse(null);
    }

    public List<ReportRequestDto> getReportsForProject(Long projectId) {
        List<Report> reports = reportRepository.findByProjectId(projectId);
        return reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // @Override
    // public Double calculateEfficiency(Long taskId) {
    // TaskEntity task = taskRepository.findById(taskId).orElse(null);
    // if (task != null) {
    // int tasksCompleted = task.getTasksCompleted();
    // int totalTasks = task.getTotalTasks();
    // if (totalTasks == 0) {
    // return 0.0; // To handle division by zero scenario
    // }
    // return ((double) tasksCompleted / totalTasks) * 100.0;
    // }
    // return 0.0; // Or throw an exception based on your error handling strategy
    // }
    private ReportRequestDto convertToDto(Report report) {
        return modelMapper.map(report, ReportRequestDto.class);
    }
    // @Override
    // public ReportDTO createReport(ReportDTO reportDTO) {
    // Report report = convertToEntity(reportDTO);
    // Report savedReport = reportRepository.save(report);
    // return convertToDto(savedReport);
    // }
    // @Override
    // public ReportDTO updateReport(Long id, ReportDTO reportDTO) {
    // Optional<Report> optionalReport = reportRepository.findById(id);
    // if (optionalReport.isPresent()) {
    // Report report = optionalReport.get();
    // // Update fields as needed
    // report.setStartDate(reportDTO.getStartDate());
    // report.setEndDate(reportDTO.getEndDate());
    // // ... update other fields
    // Report updatedReport = reportRepository.save(report);
    // return convertToDto(updatedReport);
    // }
    // return null;
    // }
    // @Override
    // public void deleteReport(Long id) {
    // reportRepository.deleteById(id);
    // }
}