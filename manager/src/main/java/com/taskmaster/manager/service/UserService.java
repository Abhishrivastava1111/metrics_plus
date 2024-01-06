package com.taskmaster.manager.service;

import java.util.List;
import com.taskmaster.manager.dto.AuthUser;
import com.taskmaster.manager.dto.UserRequest;
import com.taskmaster.manager.dto.UserResponse;

public interface UserService {
    List<UserResponse> listOfUsers();

    UserResponse createUser(UserRequest user);

    UserResponse updateUser(Long id, UserRequest user);

    UserResponse deleteUser(Long id);

    UserResponse getUserById(Long id);

    UserResponse userLogin(AuthUser userData);
}