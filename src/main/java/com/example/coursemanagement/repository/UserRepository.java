package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT COUNT(u) FROM UserEntity u")
    Long countAllUsers();

    @Query("SELECT DISTINCT YEAR(u.registrationDate) FROM UserEntity u ORDER BY YEAR(u.registrationDate)")
    List<Integer> findYearsOfRegistrations();

    @Query("SELECT MONTH(u.registrationDate) AS month, COUNT(u) AS userCount " +
            "FROM UserEntity u " +
            "WHERE YEAR(u.registrationDate) = :year " +
            "GROUP BY MONTH(u.registrationDate) " +
            "ORDER BY MONTH(u.registrationDate)")
    List<Object[]> findRegistrationStatsByYear(@Param("year") int year);

    @Query("SELECT YEAR(u.registrationDate) AS year, COUNT(u) AS userCount " +
            "FROM UserEntity u " +
            "WHERE u.role.roleId = 3 " +
            "GROUP BY YEAR(u.registrationDate) " +
            "ORDER BY YEAR(u.registrationDate)")
    List<Object[]> countRegistrationsByYear();

    @Query("SELECT YEAR(u.registrationDate) AS year, MONTH(u.registrationDate) AS month, DAY(u.registrationDate) AS day, COUNT(u) AS userCount " +
            "FROM UserEntity u " +
            "WHERE u.role.roleId = 3 " +
            "GROUP BY YEAR(u.registrationDate), MONTH(u.registrationDate), DAY(u.registrationDate) " +
            "ORDER BY YEAR(u.registrationDate), MONTH(u.registrationDate), DAY(u.registrationDate)")
    List<Object[]> countRegistrationsByDay();
}
