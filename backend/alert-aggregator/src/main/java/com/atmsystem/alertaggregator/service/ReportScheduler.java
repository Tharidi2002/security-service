package com.atmsystem.alertaggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportScheduler {

    private final ReportingService reportingService;

    /**
     * Runs every day at 8:00 AM to generate and send the daily alert summary.
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void scheduleDailyReport() {
        log.info("Executing scheduled task: Generate Daily Report");
        reportingService.generateAndSendDailyReport();
    }
}
