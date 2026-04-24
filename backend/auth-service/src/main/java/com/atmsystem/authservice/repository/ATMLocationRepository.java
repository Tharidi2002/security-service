package com.atmsystem.authservice.repository;

import com.atmsystem.authservice.model.ATMLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ATMLocationRepository extends JpaRepository<ATMLocation, Long> {
    
    Optional<ATMLocation> findByAtmId(String atmId);
    boolean existsByAtmId(String atmId);
    
    List<ATMLocation> findByBankCode(String bankCode);
    List<ATMLocation> findByStatus(ATMLocation.Status status);
    List<ATMLocation> findByCity(String city);
    List<ATMLocation> findByDistrict(String district);
    
    @Query("SELECT a FROM ATMLocation a WHERE a.bankCode = :bankCode AND a.status = :status")
    List<ATMLocation> findByBankCodeAndStatus(@Param("bankCode") String bankCode, 
                                            @Param("status") ATMLocation.Status status);
    
    @Query("SELECT a FROM ATMLocation a WHERE a.location LIKE %:location% OR a.address LIKE %:location%")
    List<ATMLocation> searchByLocation(@Param("location") String location);
    
    @Query("SELECT COUNT(a) FROM ATMLocation a WHERE a.bankCode = :bankCode AND a.status = 'ACTIVE'")
    long countActiveByBankCode(@Param("bankCode") String bankCode);
    
    void deleteByAtmId(String atmId);
}
