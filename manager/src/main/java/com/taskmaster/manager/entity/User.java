package com.taskmaster.manager.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users") // Specify the table name in PostgreSQL
@Setter
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot be longer than 100 characters")
    @Column(name = "first_name")
    private String fname;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot be longer than 100 characters")
    @Column(name = "last_name")
    private String lname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email cannot be longer than 255 characters")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Contact is required")
    @Size(max = 12, message = "Contact cannot be longer than 12 characters")
    @Column(name = "contact", unique = true)
    private String contact;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot be longer than 255 characters")
    @Column(name = "address")
    private String address;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "joining_date", updatable = false)
    private Date joiningDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updation_date")
    private Date updationDate;

    @Column(name = "disabled", nullable = false)
    private boolean disabled;

    @Column(name = "status")
    private String status = "undeployed";

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "user")
    private Set<UserProject> userProjects;

    @OneToMany(mappedBy = "senderUser")
    private Set<Message> senderMsg;

    @OneToMany(mappedBy = "receiverUser")
    private Set<Message> receiverMsg;

    @OneToMany(mappedBy = "user")
    private Set<Report> reports;

}
