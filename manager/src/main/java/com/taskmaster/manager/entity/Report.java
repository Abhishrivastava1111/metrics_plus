package com.taskmaster.manager.entity;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Report extends BaseEntity {

    @NotNull(message = "Start date is required")
    private Date startDate;

    @NotNull(message = "End date is required")
    private Date endDate;

    @NotBlank(message = "Status is required")
    private String status;

    // Summary or description of the report
    @Column(length = 1000)
    @NotBlank(message = "Summary is required")
    @Size(max = 1000, message = "Summary cannot be longer than 1000 characters")
    private String summary;

    // Metrics or statistics
    @NotNull(message = "Number of tasks completed is required")
    @Min(value = 0, message = "Number of tasks completed must be at least 0")
    private Long numberOfTasksCompleted;

    // Evaluation or remarks
    @Column(length = 500)
    @Size(max = 500, message = "Evaluation cannot be longer than 500 characters")
    private String evaluation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @NotNull(message = "Project is required")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "worklog_id")
    @NotNull(message = "Worklog is required")
    private Worklog workLog;
}
