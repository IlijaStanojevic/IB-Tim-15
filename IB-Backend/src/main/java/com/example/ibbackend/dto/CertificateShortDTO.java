package com.example.ibbackend.dto;

import com.example.ibbackend.model.Certificate;

import java.time.LocalDate;

public class CertificateShortDTO {
    private String serialNumber;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Certificate.CertificateType type;
    private String owner;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public CertificateShortDTO(String serialNumber, LocalDate validFrom, LocalDate validTo, Certificate.CertificateType type, String owner) {
        this.serialNumber = serialNumber;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.type = type;
        this.owner = owner;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public Certificate.CertificateType getType() {
        return type;
    }

    public void setType(Certificate.CertificateType type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
