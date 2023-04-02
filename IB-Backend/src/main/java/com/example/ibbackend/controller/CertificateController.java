package com.example.IBBackend.controller;

import com.example.IBBackend.dto.CertificateContract;
import com.example.IBBackend.dto.CertificateShortDTO;
import com.example.IBBackend.model.Certificate;
import com.example.IBBackend.model.CertificateRequest;
import com.example.IBBackend.model.User;
import com.example.IBBackend.repository.CertificateRepository;
import com.example.IBBackend.service.CertRequestsService;
import com.example.IBBackend.service.CertificateGeneratorService;
import com.example.IBBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {
    @Autowired
    private CertificateGeneratorService generatorService;
    @Autowired
    private CertRequestsService requestsService;
    @Autowired
    private UserService userService;

    @Autowired
    private CertificateRepository certificateRepository;

    @GetMapping("api/certs/")
    public List<CertificateShortDTO> getAll(){


        return certificateRepository.findAll().stream()
                        .map(c -> new CertificateShortDTO(c.getValidFrom(), c.getType(), c.getUsername()))
                        .collect(Collectors.toList());
    }
//    @PostMapping("api/certs/issue")
//    public ResponseEntity issueCertificate(@RequestBody CertificateContract contract){
//        try{
//            Certificate certificate = generatorService.issueCertificate(contract.getIssuerSN(), contract.getSubjectEmail()
//                    , contract.getKeyUsageFlags(), contract.getDate());
//            return new ResponseEntity(certificate, HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }

//    }

    @PostMapping("api/certs/request")
    public ResponseEntity requestCertificate(@RequestBody CertificateRequest request, Authentication auth){
        try{
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                requestsService.addRequest(request, true);
                Certificate certificate = generatorService.issueCertificate(request.getContract().getIssuerSN(), request.getContract().getSubjectEmail()
                        , request.getContract().getKeyUsageFlags(), request.getContract().getDate());
                return new ResponseEntity(request, HttpStatus.OK);
            }else{
                if (request.calculateType() == Certificate.CertificateType.Root){
                    return new ResponseEntity("Non admin users cannot create root certs", HttpStatus.BAD_REQUEST);
                }
                Optional<Certificate> targetedCert = certificateRepository.findCertificateBySerialNumber(request.getContract().getIssuerSN());
                if (targetedCert.isEmpty()){
                    return new ResponseEntity("Non existent issuer serial number", HttpStatus.NOT_FOUND);
                }
                if (requestsService.addRequest(request, false)){
                    Certificate certificate = generatorService.issueCertificate(request.getContract().getIssuerSN(), request.getContract().getSubjectEmail()
                            , request.getContract().getKeyUsageFlags(), request.getContract().getDate());
                }

                return new ResponseEntity(request, HttpStatus.OK);
            }

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
