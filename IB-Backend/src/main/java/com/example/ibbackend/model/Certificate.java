package com.example.IBBackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "certificates")
public class Certificate
{
    @Id
    private String id;

    private String serialNumber;
    private String issuer;
    private LocalDate validFrom;
    private LocalDate validTo;
    private boolean isValid;
    private CertificateType type;
    private String username;

    public enum CertificateType{
        Root,Intermediate,End
    }

    public Certificate() {
        this.id = "";
        this.serialNumber = "";
        this.issuer = "";
        this.validFrom = null;
        this.validTo = null;
        this.isValid = false;
        this.type = null;
        this.username = "";
    }

    public Certificate(String id, String serialNumber, String issuer, LocalDate validFrom, LocalDate validTo, boolean isValid, CertificateType type, String username) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.issuer = issuer;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.isValid = isValid;
        this.type = type;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }

    public CertificateType getType() {
        return type;
    }

    public void setType(CertificateType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}