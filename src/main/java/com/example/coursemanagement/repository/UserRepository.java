package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findUserByUserId(Integer userId);
}
