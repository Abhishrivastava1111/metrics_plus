package com.taskmaster.manager.entity;

import jakarta.persistence.*;

@Entity
public class UserRole extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
