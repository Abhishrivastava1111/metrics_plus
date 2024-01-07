package com.taskmaster.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.taskmaster.manager.dto.AuthUser;
import com.taskmaster.manager.dto.RequestToken;
import com.taskmaster.manager.dto.TokenDto;
import com.taskmaster.manager.dto.UserRequest;
import com.taskmaster.manager.dto.UserResponse;
import com.taskmaster.manager.service.UserService;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userResponse) {
        UserResponse createdUser = userService.createUser(userResponse);
        return ResponseEntity.ok(createdUser);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> userLogin(@RequestBody AuthUser authUser) {
        return userService.userLogin(authUser);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest userReuest, @PathVariable Long id) {
        UserResponse user = userService.updateUser(id, userReuest);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        UserResponse user = userService.deleteUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("reGenerateToken")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> generateTokenFromRefreshToken(@RequestBody RequestToken refreshToken) {
        return userService.generateTokenFromRefreshToken(refreshToken);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUserList() {
        List<UserResponse> userList = userService.listOfUsers();
        return ResponseEntity.ok(userList);
    }
}