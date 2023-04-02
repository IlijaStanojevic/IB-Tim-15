package com.example.IBBackend.service;

import com.example.IBBackend.model.Certificate;
import com.example.IBBackend.model.CertificateRequest;
import com.example.IBBackend.repository.CertificateRepository;
import com.example.IBBackend.repository.RequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertRequestsService {
    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    public List<CertificateRequest> findAll() {
        return requestsRepository.findAll();
    }
    public List<CertificateRequest> findByRequester(String requester){return requestsRepository.findCertificateRequestsByRequester(requester);}
    public boolean addRequest(CertificateRequest request, boolean isAdmin){
        if (isAdmin){
            request.setMessage("Accepted for admin automatically");
            request.setState(CertificateRequest.RequestState.ACCEPTED);
            request.setUserWhoDecides("admin");
        }else{
            Optional<Certificate> targetedCert = certificateRepository.findCertificateBySerialNumber(request.getContract().getIssuerSN());
            if (targetedCert.get().getUsername().equals(request.getRequester())){
                request.setMessage("Accepted for same owner automatically");
                request.setState(CertificateRequest.RequestState.ACCEPTED);
                request.setUserWhoDecides(targetedCert.get().getUsername());
            }else{
                request.setMessage("");
                request.setState(CertificateRequest.RequestState.PENDING);
                if (targetedCert.get().getType() == Certificate.CertificateType.Root){
                    request.setUserWhoDecides("admin");
                }else{
                    request.setUserWhoDecides(targetedCert.get().getUsername());
                }
            }

        }
        request.setType(request.calculateType());
        requestsRepository.save(request);
        return request.getState() == CertificateRequest.RequestState.ACCEPTED;
    }
}
