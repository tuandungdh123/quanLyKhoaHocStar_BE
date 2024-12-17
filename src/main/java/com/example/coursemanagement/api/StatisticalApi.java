package com.example.coursemanagement.api;

import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.repository.PaymentRepository;
import com.example.coursemanagement.service.StatisticalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/statistical")
@CrossOrigin(origins = "*")
public class StatisticalApi {
    @Autowired
    private StatisticalService statisticalService;
    private PaymentRepository paymentRepository;
    @GetMapping("/getTotalUsers")
    public ResponseObject<?> getTotalUsers() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(statisticalService.getTotalUsers());
            resultApi.setSuccess(true);
            resultApi.setMessage("Total users retrieved successfully.");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Error fetching total users", e);
        }
        return resultApi;
    }


    @GetMapping("/getAvailableUserYears")
    public ResponseObject<?> getAvailableUserYears() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(statisticalService.getAvailableUserYears());
            resultApi.setSuccess(true);
            resultApi.setMessage("Available years retrieved successfully.");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Error fetching available user years", e);
        }
        return resultApi;
    }

    @GetMapping("/getAvailableRevenueYears")
    public ResponseObject<?> getAvailableRevenueYears() {
        var resultApi = new ResponseObject<>();
        try {
            var years = statisticalService.getAvailableRevenueYears();
            resultApi.setData(years);
            resultApi.setSuccess(true);
            resultApi.setMessage("Fetched enrollment statistics successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to get enrollment statistics", e);
        }
        return resultApi;
    }

//    @GetMapping("/getRegistrationStats/yearly")
//    public ResponseObject<?> getYearlyStats() {
//        var resultApi = new ResponseObject<>();
//        try {
//            resultApi.setData(statisticalService.getYearlyStats());
//            resultApi.setSuccess(true);
//            resultApi.setMessage("Yearly statistics retrieved successfully.");
//        } catch (Exception e) {
//            resultApi.setSuccess(false);
//            resultApi.setMessage(e.getMessage());
//            log.error("Error fetching yearly statistics", e);
//        }
//        return resultApi;
//    }

    @GetMapping("/getRegistrationStats/monthly")
    public ResponseObject<?> getRegistrationStatsByYear(@RequestParam(required = false, defaultValue = "2024") Integer year) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(statisticalService.getRegistrationStats(year));
            resultApi.setSuccess(true);
            resultApi.setMessage("Monthly statistics retrieved successfully.");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/statistical/getRegistrationStats/monthly", e);
        }
        return resultApi;
    }


    @GetMapping("/getRegistrationStats/daily")
    public ResponseObject<?> getDailyStats() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(statisticalService.getDailyStats());
            resultApi.setSuccess(true);
            resultApi.setMessage("Daily statistics retrieved successfully.");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Error fetching daily statistics", e);
        }
        return resultApi;
    }

    @GetMapping("/revenue/statistics")
    public ResponseObject<?> getMonthlyRevenueStatistics(@RequestParam int year) {
        var resultApi = new ResponseObject<>();
        try {
            var statistics = statisticalService.getMonthlyRevenueStatistics(year);
            resultApi.setData(statistics);
            resultApi.setSuccess(true);
            resultApi.setMessage("Fetched monthly revenue statistics successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to get monthly revenue statistics", e);
        }
        return resultApi;
    }

    @GetMapping("/revenue/total")
    public ResponseObject<?> getTotalRevenue() {
        var resultApi = new ResponseObject<>();
        try {
            Long totalRevenue = statisticalService.getTotalRevenue();
            resultApi.setData(totalRevenue);
            resultApi.setSuccess(true);
            resultApi.setMessage("Total revenue retrieved successfully.");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Error fetching total revenue", e);
        }
        return resultApi;
    }

    @GetMapping("/revenue/today")
    public ResponseObject<?> getTodayRevenue() {
        var resultApi = new ResponseObject<>();
        try {
            var todayRevenue = statisticalService.getTodayRevenue();
            resultApi.setData(todayRevenue);
            resultApi.setSuccess(true);
            resultApi.setMessage("Fetched today's revenue successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to get today's revenue", e);
        }
        return resultApi;
    }


}
