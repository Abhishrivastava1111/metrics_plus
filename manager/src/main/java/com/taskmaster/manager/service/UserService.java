package com.taskmaster.manager.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.taskmaster.manager.dto.AuthUser;
import com.taskmaster.manager.dto.RequestToken;
import com.taskmaster.manager.dto.TokenDto;
import com.taskmaster.manager.dto.UserRequest;
import com.taskmaster.manager.dto.UserResponse;

public interface UserService {
    List<UserResponse> listOfUsers();

    UserResponse createUser(UserRequest user);

    UserResponse updateUser(Long id, UserRequest user);

    UserResponse deleteUser(Long id);

    UserResponse getUserById(Long id);

    ResponseEntity<TokenDto> userLogin(AuthUser userData);

    ResponseEntity<String> generateTokenFromRefreshToken(RequestToken refreshToken);
}
