package com.example.ibbackend.repository;

import com.example.ibbackend.model.CertificateRequest;
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
    public Optional<CertificateRequest> findCertificateRequestsById(String id);
}
