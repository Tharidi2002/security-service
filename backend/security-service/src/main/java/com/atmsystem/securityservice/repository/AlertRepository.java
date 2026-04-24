package com.atmsystem.securityservice.repository;

import com.atmsystem.securityservice.model.Alert;
import com.atmsystem.securityservice.model.Severity;
import com.atmsystem.securityservice.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByAtmId(String atmId);
    List<Alert> findBySeverity(Severity severity);
    List<Alert> findByStatus(Status status);

}