package com.taskmaster.manager.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Project extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false, length = 1000)
    private String description;

    @OneToMany(mappedBy = "project")
    private Set<Worklog> worklogs;

    @OneToMany(mappedBy = "project")
    private Set<UserProject> userProjects;

}
