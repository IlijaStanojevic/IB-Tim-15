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
    private boolean status;
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
        this.status = false;
        this.type = null;
        this.username = "";
    }

    public Certificate(String id, String serialNumber, String issuer, LocalDate validFrom, LocalDate validTo, boolean status, CertificateType type, String username) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.issuer = issuer;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.type = type;
        this.username = username;
    }
}