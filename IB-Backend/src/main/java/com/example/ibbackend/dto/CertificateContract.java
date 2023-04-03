package com.example.ibbackend.dto;

import java.time.LocalDateTime;

public class CertificateContract {

    private String subjectEmail;

    private String keyUsageFlags;


    private String issuerSN;

    private LocalDateTime date;

    public CertificateContract() {
    }

    public CertificateContract(String subjectEmail, String keyUsageFlags, String issuerSN, LocalDateTime date) {
        this.subjectEmail = subjectEmail;
        this.keyUsageFlags = keyUsageFlags;
        this.issuerSN = issuerSN;
        this.date = date;
    }

    public String getSubjectEmail() {
        return subjectEmail;
    }

    public void setSubjectEmail(String subjectEmail) {
        this.subjectEmail = subjectEmail;
    }

    public String getKeyUsageFlags() {
        return keyUsageFlags;
    }

    public void setKeyUsageFlags(String keyUsageFlags) {
        this.keyUsageFlags = keyUsageFlags;
    }

    public String getIssuerSN() {
        return issuerSN;
    }

    public void setIssuerSN(String issuerSN) {
        this.issuerSN = issuerSN;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
