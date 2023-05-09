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
import org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(maxAge = 3600)
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
    public List<CertificateShortDTO> getAll() {
        return certificateRepository.findAll().stream()
                .map(c -> new CertificateShortDTO(c.getSerialNumber(), c.getValidFrom(), c.getValidTo(), c.getType(), c.getUsername()))
                .collect(Collectors.toList());
    }
    @GetMapping("/api/certs/mycerts")
    public List<CertificateShortDTO> getMyCerts(Authentication authentication) {
        return certificateRepository.findAllByUsername(authentication.getName()).stream()
                .map(c -> new CertificateShortDTO(c.getSerialNumber(), c.getValidFrom(), c.getValidTo(), c.getType(), c.getUsername()))
                .collect(Collectors.toList());
    }


    @GetMapping("/api/certs/{id}/download")
    public ResponseEntity downloadCert(@PathVariable String id) throws FileNotFoundException, CertificateException {
        if (certificateRepository.findCertificateBySerialNumber(id).isEmpty()) {
            return new ResponseEntity("Certificate not found", HttpStatus.NOT_FOUND);
        }
        FileSystemResource fsr = generatorService.downloadCertificate(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", id + ".crt");
        return ResponseEntity.ok().headers(headers).body(fsr);
    }// TODO add private key downloading

    @GetMapping("/api/certs/{id}/validate")
    public ResponseEntity validateSerialNumber(@PathVariable String id) {
        if (certificateRepository.findCertificateBySerialNumber(id).isEmpty()) {
            return new ResponseEntity("Certificate not found", HttpStatus.NOT_FOUND);
        }
        if (!generatorService.validateCertificate(id)) {
            return new ResponseEntity(id + " is not valid", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(id + " is valid", HttpStatus.OK);
        }
    }

    @PostMapping("/api/certs/validate/upload")
    public ResponseEntity validateUploadedFile(@RequestParam("file") MultipartFile file) {
        try {
            String serialNumber = generatorService.getSerialNumberFromFile(file);
            if (!generatorService.validateCertificate(serialNumber)) {
                return new ResponseEntity(serialNumber + " is not valid", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity(serialNumber + " is valid", HttpStatus.OK);
            }
        } catch (IOException e) {
            return new ResponseEntity("Error processing file", HttpStatus.BAD_REQUEST);
        } catch (CertificateException e) {
            return new ResponseEntity("Error processing the certificate", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity requestCertificate(@RequestBody CertificateRequest request, Authentication auth) {
        try {
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                requestsService.addRequest(request, true);
                Certificate certificate = generatorService.issueCertificate(request.getContract().getIssuerSN(), request.getContract().getSubjectEmail()
                        , request.getContract().getKeyUsageFlags(), request.getContract().getDate());
                return new ResponseEntity(request, HttpStatus.OK);
            } else {
                if (request.calculateType() == Certificate.CertificateType.Root) {
                    return new ResponseEntity("Non admin users cannot create root certs", HttpStatus.BAD_REQUEST);
                }
                Optional<Certificate> targetedCert = certificateRepository.findCertificateBySerialNumber(request.getContract().getIssuerSN());
                if (targetedCert.isEmpty()) {
                    return new ResponseEntity("Non existent issuer serial number", HttpStatus.NOT_FOUND);
                }
                if (requestsService.addRequest(request, false)) {
                    Certificate certificate = generatorService.issueCertificate(request.getContract().getIssuerSN(), request.getContract().getSubjectEmail()
                            , request.getContract().getKeyUsageFlags(), request.getContract().getDate());
                }

                return new ResponseEntity(request, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
