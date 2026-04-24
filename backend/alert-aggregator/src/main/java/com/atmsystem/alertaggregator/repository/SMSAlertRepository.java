package com.atmsystem.alertaggregator.repository;

import com.atmsystem.alertaggregator.model.SMSAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SMSAlertRepository extends JpaRepository<SMSAlert, Long> {
    
    List<SMSAlert> findByAtmId(String atmId);
    List<SMSAlert> findByBankCode(String bankCode);
    List<SMSAlert> findByProcessed(boolean processed);
    List<SMSAlert> findBySeverity(SMSAlert.Severity severity);
    List<SMSAlert> findByAlertType(SMSAlert.AlertType alertType);
    
    @Query("SELECT a FROM SMSAlert a WHERE a.timestamp BETWEEN :startDate AND :endDate ORDER BY a.timestamp DESC")
    List<SMSAlert> findByTimestampBetween(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM SMSAlert a WHERE a.processed = false AND a.severity = :severity ORDER BY a.timestamp DESC")
    List<SMSAlert> findUnprocessedBySeverity(@Param("severity") SMSAlert.Severity severity);
    
    @Query("SELECT COUNT(a) FROM SMSAlert a WHERE a.atmId = :atmId AND a.timestamp >= :since")
    long countByAtmIdSince(@Param("atmId") String atmId, @Param("since") LocalDateTime since);
}
