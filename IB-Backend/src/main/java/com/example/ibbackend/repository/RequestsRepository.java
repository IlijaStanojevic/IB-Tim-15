package com.example.IBBackend.repository;

import com.example.IBBackend.model.CertificateRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestsRepository  extends MongoRepository<CertificateRequest, Long> {

    public List<CertificateRequest> findAll();
    public List<CertificateRequest> findCertificateRequestsByRequester(String requester);
    public List<CertificateRequest> findCertificateRequestsByContract_IssuerSN(String issuerSN);
    public List<CertificateRequest> findCertificateRequestsByUserWhoDecides(String email);
}
