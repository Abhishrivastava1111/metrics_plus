package com.taskmaster.manager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmaster.manager.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    // Add custom query methods if needed
    List<Report> findByProjectId(Long projectId);
}
