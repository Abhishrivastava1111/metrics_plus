package com.taskmaster.manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Report extends BaseEntity {
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
