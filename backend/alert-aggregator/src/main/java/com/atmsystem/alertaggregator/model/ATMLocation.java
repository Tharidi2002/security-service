package com.atmsystem.alertaggregator.model;

import lombok.Data;

@Data
public class ATMLocation {
    private String technicalContactEmail;
    private String securityContactEmail;
    private String operationsContactEmail;
    private String city;
    private String district;
    private String address;
}
