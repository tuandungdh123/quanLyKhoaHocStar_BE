package com.example.coursemanagement.repository;

import com.example.coursemanagement.payment.data.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    @Query("SELECT MONTH(p.paymentDate) AS month, SUM(p.amount) AS totalRevenue " +
            "FROM PaymentEntity p " +
            "WHERE p.paymentStatus = 'completed' AND YEAR(p.paymentDate) = :year " +
            "GROUP BY MONTH(p.paymentDate) " +
            "ORDER BY MONTH(p.paymentDate)")
    List<Object[]> calculateMonthlyRevenue(@Param("year") int year);

    @Query("SELECT DISTINCT YEAR(p.paymentDate) FROM PaymentEntity p ORDER BY YEAR(p.paymentDate)")
    List<Integer> findDistinctYears();

    @Query("SELECT SUM(p.amount) FROM PaymentEntity p WHERE p.paymentStatus = 'completed'")
    Long calculateTotalRevenue();
}
