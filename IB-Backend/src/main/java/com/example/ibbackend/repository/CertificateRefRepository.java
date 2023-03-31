package com.example.IBBackend.repository;


import com.example.IBBackend.model.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRefRepository extends MongoRepository<Certificate, Long>
{
    

}
