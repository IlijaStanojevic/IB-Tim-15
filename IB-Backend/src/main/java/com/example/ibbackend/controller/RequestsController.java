package com.example.ibbackend.controller;

import com.example.ibbackend.model.Certificate;
import com.example.ibbackend.model.CertificateRequest;
import com.example.ibbackend.repository.CertificateRepository;
import com.example.ibbackend.repository.RequestsRepository;
import com.example.ibbackend.service.CertRequestsService;
import com.example.ibbackend.service.CertificateGeneratorService;
import com.example.ibbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestsController {
    @Autowired
    private CertificateGeneratorService generatorService;
    @Autowired
    private CertRequestsService requestsService;
    @Autowired
    private UserService userService;

    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private RequestsRepository requestsRepository;

    @GetMapping("api/certs/requests")
    public ResponseEntity getAll(Authentication authentication){
        List<CertificateRequest> certificateRequests = requestsService.findByRequester(authentication.getName());
        return new ResponseEntity<>(certificateRequests, HttpStatus.OK);
    }
    @PutMapping("api/certs/{id}/accept")
    public ResponseEntity acceptRequest(@PathVariable String id, Authentication authentication){
        try{
            Optional<CertificateRequest> request = requestsService.findById(id);
            if (request.isEmpty()){
                return new ResponseEntity("Request not found", HttpStatus.NOT_FOUND);
            }
            requestsService.acceptRequest(id, authentication.getName());
            Certificate cert = generatorService.issueCertificate(request.get().getContract().getIssuerSN(), request.get().getContract().getSubjectEmail(),
                    request.get().getContract().getKeyUsageFlags(), request.get().getContract().getDate());
            return new ResponseEntity(cert, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("api/certs/{id}/decline")
    public ResponseEntity declineRequest(@PathVariable String id,@RequestBody String reasonForDeclining, Authentication authentication){
        try{
            Optional<CertificateRequest> request = requestsService.findById(id);
            if (request.isEmpty()){
                return new ResponseEntity("Request not found", HttpStatus.NOT_FOUND);
            }
            requestsService.declineRequest(id, authentication.getName(), reasonForDeclining);
            return new ResponseEntity("Request declined!", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
