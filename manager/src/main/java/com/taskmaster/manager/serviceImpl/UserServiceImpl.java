package com.taskmaster.manager.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taskmaster.manager.dto.AuthUser;
import com.taskmaster.manager.dto.UserRequest;
import com.taskmaster.manager.dto.UserResponse;
import com.taskmaster.manager.entity.Role;
import com.taskmaster.manager.entity.User;
import com.taskmaster.manager.entity.UserRole;
import com.taskmaster.manager.exception.UserNotFoundException;
import com.taskmaster.manager.repository.RoleRepository;
import com.taskmaster.manager.repository.UserRepository;
import com.taskmaster.manager.repository.UserRoleRepository;
import com.taskmaster.manager.service.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRoleRepository userRoleRepo;

    @Override
    public UserResponse createUser(UserRequest user) {
        User newUser = mapper.map(user, User.class);
        Role role = roleRepo.findByName(user.getRole());
        // todo check the null of role
        UserRole userRole = userRoleRepo.save(new UserRole(newUser, role));
        Set<UserRole> roleList = new HashSet<>();
        roleList.add(userRole);
        // todo -> check the null of the set of usr roles
        newUser.setUserRoles(roleList);
        User userDetails = userRepo.save(newUser);
        UserResponse userResponse = mapper.map(userDetails, UserResponse.class);
        userDetails.getUserRoles().stream().forEach((e) -> userResponse.setRole(e.getRole().getName()));
        return userResponse;
    }

    @Transactional
    @Override
    public UserResponse updateUser(Long id, UserRequest user) {
        User currentUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id:" + id + " not found !"));
        Role role = roleRepo.findByName(user.getRole());
        currentUser.setFname(user.getFname());
        currentUser.setLname(user.getLname());
        currentUser.setEmail(user.getEmail());
        currentUser.setContact(user.getContact());
        currentUser.setPassword(user.getPassword());
        currentUser.setAddress(user.getAddress());
        currentUser.setUpdationDate(new Date());
        Set<UserRole> roleList = currentUser.getUserRoles();
        UserRole userRole = userRoleRepo.save(new UserRole(currentUser, role));
        roleList.add(userRole);
        currentUser.setUserRoles(roleList);
        UserResponse userResponse = mapper.map(currentUser, UserResponse.class);
        currentUser.getUserRoles().stream().forEach((e) -> userResponse.setRole(e.getRole().getName()));
        return userResponse;
    }

    @Transactional
    @Override
    public UserResponse deleteUser(Long id) {
        User currentUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id:" + id + " not found !"));
        currentUser.setDisabled(true);
        UserResponse userResponse = mapper.map(currentUser, UserResponse.class);
        currentUser.getUserRoles().stream().forEach((e) -> userResponse.setRole(e.getRole().getName()));
        return userResponse;
    }

    @Override
    public UserResponse getUserById(Long id) {
        User currentUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id:" + id + " not found !"));
        UserResponse userDetails = mapper.map(currentUser, UserResponse.class);
        currentUser.getUserRoles().stream().forEach((e) -> userDetails.setRole(e.getRole().getName()));
        return userDetails;
    }

    @Transactional
    @Override
    public List<UserResponse> listOfUsers() {
        List<User> userList = userRepo.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList) {
            UserResponse userResp = mapper.map(user, UserResponse.class);
            user.getUserRoles().stream().forEach((e) -> userResp.setRole(e.getRole().getName()));
            userResponseList.add(userResp);
        }
        Collections.sort(userResponseList, new Comparator<UserResponse>() {
            @Override
            public int compare(UserResponse user1, UserResponse user2) {
                Date date1 = user1.getJoiningDate();
                Date date2 = user2.getJoiningDate();
                return date1.compareTo(date2);
            }
        });
        return userResponseList;
    }

    @Transactional
    @Override
    public UserResponse userLogin(AuthUser userData) {
        User currentUser = userRepo.findByEmailAndPassword(userData.getEmail(), userData.getPassword());
        if (currentUser.isDisabled()) {
            currentUser.setDisabled(false);
        }
        UserResponse userDetails = mapper.map(currentUser, UserResponse.class);
        currentUser.getUserRoles().stream().forEach((e) -> userDetails.setRole(e.getRole().getName()));
        return userDetails;
    }
}