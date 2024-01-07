package com.taskmaster.manager.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskmaster.manager.entity.User;
import com.taskmaster.manager.repository.UserRepository;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserRepository userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        List<User> userdetails = userDao.findByEmail(email);
        if (userdetails.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = userdetails.get(0);
        return new UserInfoDetails(user);
    }

}
