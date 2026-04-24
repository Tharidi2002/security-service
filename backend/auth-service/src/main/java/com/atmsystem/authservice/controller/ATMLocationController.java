package com.atmsystem.authservice.controller;

import com.atmsystem.authservice.model.ATMLocation;
import com.atmsystem.authservice.service.ATMLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atm-locations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ATMLocationController {

    private final ATMLocationService atmLocationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerATM(@RequestBody ATMLocation atmLocation) {
        try {
            ATMLocation registered = atmLocationService.registerATM(atmLocation);
            return ResponseEntity.ok(Map.of(
                "message", "ATM registered successfully",
                "atm", registered
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ATMLocation>> getAllATMs() {
        return ResponseEntity.ok(atmLocationService.getAllATMs());
    }

    @GetMapping("/{atmId}")
    public ResponseEntity<?> getATMById(@PathVariable String atmId) {
        return atmLocationService.getATMById(atmId)
                .map(atm -> ResponseEntity.ok(atm))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/bank/{bankCode}")
    public ResponseEntity<List<ATMLocation>> getATMsByBankCode(@PathVariable String bankCode) {
        return ResponseEntity.ok(atmLocationService.getATMsByBankCode(bankCode));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ATMLocation>> getATMsByStatus(@PathVariable ATMLocation.Status status) {
        return ResponseEntity.ok(atmLocationService.getATMsByStatus(status));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<ATMLocation>> getATMsByCity(@PathVariable String city) {
        return ResponseEntity.ok(atmLocationService.getATMsByCity(city));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<ATMLocation>> getATMsByDistrict(@PathVariable String district) {
        return ResponseEntity.ok(atmLocationService.getATMsByDistrict(district));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ATMLocation>> searchATMs(@RequestParam String location) {
        return ResponseEntity.ok(atmLocationService.searchATMsByLocation(location));
    }

    @PutMapping("/{atmId}")
    public ResponseEntity<?> updateATM(@PathVariable String atmId, @RequestBody ATMLocation atmLocation) {
        try {
            ATMLocation updated = atmLocationService.updateATM(atmId, atmLocation);
            return ResponseEntity.ok(Map.of(
                "message", "ATM updated successfully",
                "atm", updated
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{atmId}/status")
    public ResponseEntity<?> updateATMStatus(
            @PathVariable String atmId, 
            @RequestParam ATMLocation.Status status) {
        try {
            ATMLocation updated = atmLocationService.updateATMStatus(atmId, status);
            return ResponseEntity.ok(Map.of(
                "message", "ATM status updated successfully",
                "atm", updated
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{atmId}")
    public ResponseEntity<?> deleteATM(@PathVariable String atmId) {
        try {
            atmLocationService.deleteATM(atmId);
            return ResponseEntity.ok(Map.of("message", "ATM deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/bank/{bankCode}/active-count")
    public ResponseEntity<Map<String, Long>> getActiveATMCount(@PathVariable String bankCode) {
        long count = atmLocationService.getActiveATMCount(bankCode);
        return ResponseEntity.ok(Map.of("activeATMCount", count));
    }

    @GetMapping("/dashboard/{bankCode}")
    public ResponseEntity<Map<String, Object>> getDashboardData(@PathVariable String bankCode) {
        List<ATMLocation> allATMs = atmLocationService.getATMsByBankCode(bankCode);
        long activeCount = allATMs.stream().filter(atm -> atm.getStatus() == ATMLocation.Status.ACTIVE).count();
        long inactiveCount = allATMs.stream().filter(atm -> atm.getStatus() == ATMLocation.Status.INACTIVE).count();
        long maintenanceCount = allATMs.stream().filter(atm -> atm.getStatus() == ATMLocation.Status.UNDER_MAINTENANCE).count();

        return ResponseEntity.ok(Map.of(
            "totalATMs", allATMs.size(),
            "activeATMs", activeCount,
            "inactiveATMs", inactiveCount,
            "maintenanceATMs", maintenanceCount,
            "recentATMs", allATMs.stream().limit(5).toList()
        ));
    }
}
