package com.taskmaster.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmaster.manager.entity.Worklog;

public interface WorklogRepository extends JpaRepository<Worklog, Long> {

}
