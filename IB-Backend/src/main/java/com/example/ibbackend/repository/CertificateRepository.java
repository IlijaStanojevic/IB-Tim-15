package com.example.IBBackend.repository;


import com.example.IBBackend.model.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends MongoRepository<Certificate, Long>
{
    public List<Certificate> findAll();

    public Optional<Certificate> findCertificateBySerialNumber(String serialNumber);


}
