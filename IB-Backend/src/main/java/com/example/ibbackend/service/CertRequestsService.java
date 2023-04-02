package com.example.ibbackend.service;

import com.example.ibbackend.model.Certificate;
import com.example.ibbackend.model.CertificateRequest;
import com.example.ibbackend.repository.CertificateRepository;
import com.example.ibbackend.repository.RequestsRepository;
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
    public List<CertificateRequest> findForUserToDecide(String email){
        return requestsRepository.findCertificateRequestsByUserWhoDecides(email);
    }
    public Optional<CertificateRequest> findById(String id){return requestsRepository.findCertificateRequestsById(id);}
    public void acceptRequest(String id, String email) throws Exception {
        Optional<CertificateRequest> request = requestsRepository.findCertificateRequestsById(id);
        if (!request.get().getUserWhoDecides().equals(email)){
            throw new Exception("You don't have the authority to accept this request");
        }
        request.get().setState(CertificateRequest.RequestState.ACCEPTED);
        requestsRepository.save(request.get());
    }
    public void declineRequest(String id, String email, String reason) throws Exception {
        Optional<CertificateRequest> request = requestsRepository.findCertificateRequestsById(id);
        if (request.get().getUserWhoDecides().equals(email)){
            throw new Exception("You don't have the authority to decline this request");
        }
        request.get().setState(CertificateRequest.RequestState.DECLINED);
        request.get().setMessage(reason);
        requestsRepository.save(request.get());
    }
}
