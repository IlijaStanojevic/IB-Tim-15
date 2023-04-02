package com.example.ibbackend.controller;

import com.example.ibbackend.dto.CertificateShortDTO;
import com.example.ibbackend.model.Certificate;
import com.example.ibbackend.model.CertificateRequest;
import com.example.ibbackend.model.User;
import com.example.ibbackend.repository.CertificateRepository;
import com.example.ibbackend.repository.UserRepository;
import com.example.ibbackend.service.CertRequestsService;
import com.example.ibbackend.service.CertificateGeneratorService;
import com.example.ibbackend.service.UserService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/api/certs")
    public List<CertificateShortDTO> getAll(@RequestHeader (name="Authorization") String token){

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));
        String sub = payload.split("\"")[3];

        UserDetails user = userService.loadUserByUsername(sub);
        if(Objects.equals(user.getAuthorities().toString(), "[ROLE_ADMIN]")) {
            return certificateRepository.findAll().stream()
                    .map(c -> new CertificateShortDTO(c.getValidFrom(), c.getType(), c.getUsername()))
                    .collect(Collectors.toList());
        }else{
            return certificateRepository.findAllByUsername(sub).stream()
                    .map(c -> new CertificateShortDTO(c.getValidFrom(), c.getType(), c.getUsername()))
                    .collect(Collectors.toList());
        }
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

    @PostMapping("/api/certs/request")
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
