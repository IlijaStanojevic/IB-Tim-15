package com.example.IBBackend.controller;

import com.example.IBBackend.dto.CertificateContract;
import com.example.IBBackend.model.Certificate;
import com.example.IBBackend.repository.CertificateRepository;
import com.example.IBBackend.service.CertificateGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {
    @Autowired
    private CertificateGeneratorService generatorService;

    @Autowired
    private CertificateRepository certificateRepository;

    @GetMapping("api/certs/")
    public List<Certificate> getAll(){
        return certificateRepository.findAll();
    }
    @PostMapping("api/certs/issue")
    public ResponseEntity issueCertificate(@RequestBody CertificateContract request){
        try{
            Certificate certificate = generatorService.issueCertificate(request.getIssuerSN(), request.getSubjectEmail()
                    , request.getKeyUsageFlags(), request.getDate());
            return new ResponseEntity(certificate, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
