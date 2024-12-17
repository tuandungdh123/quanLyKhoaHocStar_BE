package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.RegistrationStatsDTO;

import java.util.List;
import java.util.Map;

public interface StatisticalService {
    Long getTotalUsers();
    Map<String, Long> getRegistrationStats(int year);
    List<RegistrationStatsDTO> getDailyStats();
    List<Integer> getAvailableUserYears();
    Map<String, Long> getMonthlyRevenueStatistics(int year);
    List<Integer> getAvailableRevenueYears();
    Long getTotalRevenue();
}
