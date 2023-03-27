package com.example.ibbackend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Table(name = "certificate_refs")
@Entity
public class CertificateRef
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private boolean valid;
    private LocalDate startDate;
    private LocalDate endDate;
    private String issuer;

    public CertificateRef()
    {
        super();
    }

    public CertificateRef(boolean valid, LocalDate startDate, LocalDate endDate, String issuer)
    {
        super();

        this.valid = valid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.issuer = issuer;
    }

    public void setValid(boolean valid)
    {
        this.valid = valid;
    }

    public boolean isValid()
    {
        return this.valid;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getStartDate()
    {
        return this.startDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public LocalDate getEndDate()
    {
        return this.endDate;
    }

    public void setIssuer(String issuer)
    {
        this.issuer = issuer;
    }

    public String getIssuer()
    {
        return this.issuer;
    }
}