package com.example.IBBackend.repository;


import com.example.IBBackend.model.CertificateRef;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CertificateRefRepository extends MongoRepository<CertificateRef, Long>
{

}
