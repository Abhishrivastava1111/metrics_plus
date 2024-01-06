package com.taskmaster.manager.dto;

import java.util.Date;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String fname;

    private String lname;

    private String email;

    private String contact;

    private String password;

    private String address;

    private String role;

    private Date creationDate;

    private Date joiningDate;

    private Date updationDate;

    private boolean disabled;

    private String status;

}