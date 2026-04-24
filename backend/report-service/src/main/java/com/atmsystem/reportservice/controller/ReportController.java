package com.atmsystem.reportservice.controller;

import com.atmsystem.reportservice.model.AnalyticsData;
import com.atmsystem.reportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/daily/{bankCode}")
    public ResponseEntity<AnalyticsData> generateDailyReport(@PathVariable String bankCode) {
        return ResponseEntity.ok(reportService.generateDailyReport(bankCode));
    }

    @PostMapping("/weekly/{bankCode}")
    public ResponseEntity<AnalyticsData> generateWeeklyReport(@PathVariable String bankCode) {
        return ResponseEntity.ok(reportService.generateWeeklyReport(bankCode));
    }

    @PostMapping("/monthly/{bankCode}")
    public ResponseEntity<AnalyticsData> generateMonthlyReport(@PathVariable String bankCode) {
        return ResponseEntity.ok(reportService.generateMonthlyReport(bankCode));
    }

    @GetMapping("/bank/{bankCode}")
    public ResponseEntity<List<AnalyticsData>> getReportsByBank(@PathVariable String bankCode) {
        return ResponseEntity.ok(reportService.getReportsByBank(bankCode));
    }

    @GetMapping("/atm/{atmId}")
    public ResponseEntity<List<AnalyticsData>> getReportsByAtm(@PathVariable String atmId) {
        return ResponseEntity.ok(reportService.getReportsByAtm(atmId));
    }

    @GetMapping("/dashboard/{bankCode}")
    public ResponseEntity<Map<String, Object>> getDashboardSummary(@PathVariable String bankCode) {
        return ResponseEntity.ok(reportService.getDashboardSummary(bankCode));
    }
}
