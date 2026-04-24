package com.atmsystem.reportservice.service;

import com.atmsystem.reportservice.model.AnalyticsData;
import com.atmsystem.reportservice.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsData generateDailyReport(String bankCode) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        
        Map<String, Object> reportData = calculateReportData(bankCode, startOfDay, now);
        
        AnalyticsData analyticsData = AnalyticsData.builder()
                .bankCode(bankCode)
                .reportType("DAILY")
                .reportData(reportData.toString())
                .generatedAt(now)
                .periodStart(startOfDay)
                .periodEnd(now)
                .totalAlerts((Long) reportData.get("totalAlerts"))
                .criticalAlerts((Long) reportData.get("criticalAlerts"))
                .warningAlerts((Long) reportData.get("warningAlerts"))
                .infoAlerts((Long) reportData.get("infoAlerts"))
                .build();
        
        return analyticsRepository.save(analyticsData);
    }

    public AnalyticsData generateWeeklyReport(String bankCode) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.minusWeeks(1);
        
        Map<String, Object> reportData = calculateReportData(bankCode, startOfWeek, now);
        
        AnalyticsData analyticsData = AnalyticsData.builder()
                .bankCode(bankCode)
                .reportType("WEEKLY")
                .reportData(reportData.toString())
                .generatedAt(now)
                .periodStart(startOfWeek)
                .periodEnd(now)
                .totalAlerts((Long) reportData.get("totalAlerts"))
                .criticalAlerts((Long) reportData.get("criticalAlerts"))
                .warningAlerts((Long) reportData.get("warningAlerts"))
                .infoAlerts((Long) reportData.get("infoAlerts"))
                .build();
        
        return analyticsRepository.save(analyticsData);
    }

    public AnalyticsData generateMonthlyReport(String bankCode) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.minusMonths(1);
        
        Map<String, Object> reportData = calculateReportData(bankCode, startOfMonth, now);
        
        AnalyticsData analyticsData = AnalyticsData.builder()
                .bankCode(bankCode)
                .reportType("MONTHLY")
                .reportData(reportData.toString())
                .generatedAt(now)
                .periodStart(startOfMonth)
                .periodEnd(now)
                .totalAlerts((Long) reportData.get("totalAlerts"))
                .criticalAlerts((Long) reportData.get("criticalAlerts"))
                .warningAlerts((Long) reportData.get("warningAlerts"))
                .infoAlerts((Long) reportData.get("infoAlerts"))
                .build();
        
        return analyticsRepository.save(analyticsData);
    }

    public List<AnalyticsData> getReportsByBank(String bankCode) {
        return analyticsRepository.findByBankCode(bankCode);
    }

    public List<AnalyticsData> getReportsByAtm(String atmId) {
        return analyticsRepository.findByAtmId(atmId);
    }

    public Map<String, Object> getDashboardSummary(String bankCode) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        
        List<AnalyticsData> todayReports = analyticsRepository.findByBankCodeAndDateRange(bankCode, startOfDay, now);
        
        long totalAlerts = todayReports.stream().mapToLong(AnalyticsData::getTotalAlerts).sum();
        long criticalAlerts = todayReports.stream().mapToLong(AnalyticsData::getCriticalAlerts).sum();
        long warningAlerts = todayReports.stream().mapToLong(AnalyticsData::getWarningAlerts).sum();
        long infoAlerts = todayReports.stream().mapToLong(AnalyticsData::getInfoAlerts).sum();
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalAlerts", totalAlerts);
        summary.put("criticalAlerts", criticalAlerts);
        summary.put("warningAlerts", warningAlerts);
        summary.put("infoAlerts", infoAlerts);
        summary.put("reportCount", todayReports.size());
        summary.put("lastGenerated", todayReports.isEmpty() ? null : todayReports.get(0).getGeneratedAt());
        
        return summary;
    }

    private Map<String, Object> calculateReportData(String bankCode, LocalDateTime start, LocalDateTime end) {
        // This would typically fetch data from alert aggregator service
        // For now, we'll simulate with sample data
        
        Map<String, Object> data = new HashMap<>();
        data.put("bankCode", bankCode);
        data.put("periodStart", start);
        data.put("periodEnd", end);
        data.put("totalAlerts", (long) (Math.random() * 100));
        data.put("criticalAlerts", (long) (Math.random() * 20));
        data.put("warningAlerts", (long) (Math.random() * 30));
        data.put("infoAlerts", (long) (Math.random() * 50));
        
        return data;
    }
}
