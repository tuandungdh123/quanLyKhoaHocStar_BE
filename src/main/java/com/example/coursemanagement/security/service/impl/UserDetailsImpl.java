package com.example.coursemanagement.security.service.impl;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

@Data
public class UserDetailsImpl implements UserDetails {
    private final Integer userId;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String name;
    private final String avatarUrl;
    private final Integer roleId;
    private final Timestamp registrationDate;
    private final Boolean status;

    public UserDetailsImpl(Integer userId, String email, String password, Collection<? extends GrantedAuthority> authorities, String name, String avatarUrl, Integer roleId, Timestamp registrationDate, Boolean status) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.roleId = roleId;
        this.registrationDate = registrationDate;
        this.status = status;
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
        return email;
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

