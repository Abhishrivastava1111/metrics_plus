package com.taskmaster.manager.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task extends BaseEntity {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name of Task cannot be longer than 100 characters")
    @Column(name = "task_name")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    @Column(name = "description")
    private String desc;

    @Column(name = "status")
    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project projectId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updation_date")
    private Date updationDate;

    @OneToOne
    private User userAssignedBy;

    @OneToOne
    private User userAssignedTo;

    @ManyToOne
    @JoinColumn(name = "worklog_id")
    private Worklog worklog;
}
