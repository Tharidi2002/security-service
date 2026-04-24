package com.atmsystem.authservice.service;

import com.atmsystem.authservice.model.ATMLocation;
import com.atmsystem.authservice.repository.ATMLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ATMLocationService {

    private final ATMLocationRepository atmLocationRepository;

    public ATMLocation registerATM(ATMLocation atmLocation) {
        if (atmLocationRepository.existsByAtmId(atmLocation.getAtmId())) {
            throw new RuntimeException("ATM with ID " + atmLocation.getAtmId() + " already exists");
        }
        
        atmLocation.setCreatedAt(LocalDateTime.now());
        if (atmLocation.getStatus() == null) {
            atmLocation.setStatus(ATMLocation.Status.ACTIVE);
        }
        
        ATMLocation saved = atmLocationRepository.save(atmLocation);
        log.info("Registered new ATM: {} at {}", saved.getAtmId(), saved.getLocation());
        return saved;
    }

    public Optional<ATMLocation> getATMById(String atmId) {
        return atmLocationRepository.findByAtmId(atmId);
    }

    public List<ATMLocation> getATMsByBankCode(String bankCode) {
        return atmLocationRepository.findByBankCode(bankCode);
    }

    public List<ATMLocation> getATMsByStatus(ATMLocation.Status status) {
        return atmLocationRepository.findByStatus(status);
    }

    public List<ATMLocation> getATMsByCity(String city) {
        return atmLocationRepository.findByCity(city);
    }

    public List<ATMLocation> getATMsByDistrict(String district) {
        return atmLocationRepository.findByDistrict(district);
    }

    public List<ATMLocation> searchATMsByLocation(String location) {
        return atmLocationRepository.searchByLocation(location);
    }

    public ATMLocation updateATM(String atmId, ATMLocation atmLocation) {
        return atmLocationRepository.findByAtmId(atmId)
                .map(existing -> {
                    existing.setBankCode(atmLocation.getBankCode());
                    existing.setLocation(atmLocation.getLocation());
                    existing.setAddress(atmLocation.getAddress());
                    existing.setPhoneNumber(atmLocation.getPhoneNumber());
                    existing.setCity(atmLocation.getCity());
                    existing.setDistrict(atmLocation.getDistrict());
                    existing.setLatitude(atmLocation.getLatitude());
                    existing.setLongitude(atmLocation.getLongitude());
                    existing.setStatus(atmLocation.getStatus());
                    existing.setLastMaintenance(atmLocation.getLastMaintenance());
                    existing.setNotes(atmLocation.getNotes());
                    return atmLocationRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ATM not found with ID: " + atmId));
    }

    public void deleteATM(String atmId) {
        if (!atmLocationRepository.existsByAtmId(atmId)) {
            throw new RuntimeException("ATM not found with ID: " + atmId);
        }
        atmLocationRepository.deleteByAtmId(atmId);
        log.info("Deleted ATM: {}", atmId);
    }

    public ATMLocation updateATMStatus(String atmId, ATMLocation.Status status) {
        return atmLocationRepository.findByAtmId(atmId)
                .map(atm -> {
                    atm.setStatus(status);
                    return atmLocationRepository.save(atm);
                })
                .orElseThrow(() -> new RuntimeException("ATM not found with ID: " + atmId));
    }

    public long getActiveATMCount(String bankCode) {
        return atmLocationRepository.countActiveByBankCode(bankCode);
    }

    public List<ATMLocation> getAllATMs() {
        return atmLocationRepository.findAll();
    }
}
