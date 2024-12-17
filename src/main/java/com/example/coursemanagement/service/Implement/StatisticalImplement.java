package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.RegistrationStatsDTO;
import com.example.coursemanagement.repository.PaymentRepository;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticalImplement implements StatisticalService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Long getTotalUsers() {
        return userRepository.countAllUsers();
    }

    @Override
    public List<Integer> getAvailableUserYears() {
        return userRepository.findYearsOfRegistrations();
    }

    @Override
    public Map<String, Long> getRegistrationStats(int year) {
        List<Object[]> results = userRepository.findRegistrationStatsByYear(year);
        Map<String, Long> userStatistics = new HashMap<>();

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long userCounttotal = (Long) result[1];
            userStatistics.put("Month " + month, userCounttotal);
        }

        return userStatistics;
    }

    @Override
    public List<RegistrationStatsDTO> getDailyStats() {
        List<Object[]> stats = userRepository.countRegistrationsByDay();
        return stats.stream()
                .map(row -> new RegistrationStatsDTO((Integer) row[0], (Integer) row[1], (Integer) row[2], (Long) row[3]))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> getMonthlyRevenueStatistics(int year) {
        List<Object[]> results = paymentRepository.calculateMonthlyRevenue(year);
        Map<String, Long> statistics = new HashMap<>();

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long totalRevenue = (Long) result[1];
            statistics.put("Month " + month, totalRevenue);
        }

        return statistics;
    }

    @Override
    public List<Integer> getAvailableRevenueYears() {
        return paymentRepository.findDistinctYears();
    }

    @Override
    public Long getTotalRevenue() {
        return paymentRepository.calculateTotalRevenue();
    }

    @Override
    public Long getTodayRevenue() {
        return paymentRepository.calculateTodayRevenue();
    }


}
