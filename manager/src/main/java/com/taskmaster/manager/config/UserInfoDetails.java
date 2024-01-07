package com.taskmaster.manager.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.taskmaster.manager.entity.User;
import com.taskmaster.manager.entity.UserRole;

public class UserInfoDetails implements UserDetails {

    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(User user) {
        userName = user.getEmail();
        password = user.getPassword();
        authorities = new ArrayList<>();
        for (UserRole userRoles : user.getUserRoles()) {
            authorities.add(new SimpleGrantedAuthority(userRoles.getRole().getName()));
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
