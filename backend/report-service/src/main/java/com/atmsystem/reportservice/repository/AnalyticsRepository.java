package com.atmsystem.reportservice.repository;

import com.atmsystem.reportservice.model.AnalyticsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<AnalyticsData, Long> {
    
    List<AnalyticsData> findByBankCode(String bankCode);
    List<AnalyticsData> findByAtmId(String atmId);
    List<AnalyticsData> findByReportType(String reportType);
    
    @Query("SELECT a FROM AnalyticsData a WHERE a.bankCode = :bankCode AND a.generatedAt BETWEEN :startDate AND :endDate ORDER BY a.generatedAt DESC")
    List<AnalyticsData> findByBankCodeAndDateRange(@Param("bankCode") String bankCode, 
                                                   @Param("startDate") LocalDateTime startDate, 
                                                   @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(a) FROM AnalyticsData a WHERE a.bankCode = :bankCode AND a.periodStart >= :since")
    long countByBankCodeSince(@Param("bankCode") String bankCode, @Param("since") LocalDateTime since);
    
    @Query("SELECT COUNT(a) FROM AnalyticsData a WHERE a.atmId = :atmId AND a.periodStart >= :since")
    long countByAtmIdSince(@Param("atmId") String atmId, @Param("since") LocalDateTime since);
}
