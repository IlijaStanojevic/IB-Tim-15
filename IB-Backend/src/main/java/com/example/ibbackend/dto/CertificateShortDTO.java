package com.example.IBBackend.dto;

import com.example.IBBackend.model.Certificate;

import java.time.LocalDate;

public class CertificateShortDTO {
    private LocalDate validFrom;
    private Certificate.CertificateType type;
    private String username;

    public CertificateShortDTO(LocalDate validFrom, Certificate.CertificateType type, String username) {
        this.validFrom = validFrom;
        this.type = type;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
