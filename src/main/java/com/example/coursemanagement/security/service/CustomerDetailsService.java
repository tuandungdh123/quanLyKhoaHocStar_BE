package com.example.coursemanagement.security.service;

import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.security.service.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        UserEntity userEntity = user.get();

        // Trả về UserDetailsImpl thay vì User
        return new UserDetailsImpl(
                userEntity.getUserId(),
                userEntity.getEmail(),
                userEntity.getPasswordHash(),
                getAuthorities(userEntity.getRole().getRoleName()),
                userEntity.getName(),
                userEntity.getAvatarUrl(),
                userEntity.getRole().getRoleId(),
                userEntity.getRegistrationDate(),
                userEntity.getStatus()
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roleName) {
        return Set.of(new SimpleGrantedAuthority("ROLE_" + roleName));
    }

}
