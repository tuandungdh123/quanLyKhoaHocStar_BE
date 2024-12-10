package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhone(String phone);
    Optional<UserEntity> findUserByUserId(Integer userId);
    @Query("SELECT MONTH(u.registrationDate) AS month, COUNT(u) AS count " +
            "FROM UserEntity u " +
            "WHERE u.role.roleId = 3 " +
            "GROUP BY MONTH(u.registrationDate) " +
            "ORDER BY MONTH(u.registrationDate)")
    List<Object[]> countRegistrationsByMonthForRole3();
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
