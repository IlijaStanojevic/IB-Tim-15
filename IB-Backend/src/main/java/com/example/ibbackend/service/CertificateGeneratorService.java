package com.example.IBBackend.service;

import com.example.IBBackend.model.Certificate;
import com.example.IBBackend.model.User;
import com.example.IBBackend.repository.CertificateRepository;
import com.example.IBBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CertificateGeneratorService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    private static String certDir = "certs";

    private Certificate issuer;
    private User subject;
//    private X509KeyUsageFlags flags;
    private boolean isAuthority;
    private X509Certificate issuerCertificate;
    private LocalDateTime validTo;
//    private RSA currentRSA;
    public Certificate issueCertificate(String issuerSN, String subjectUsername, String keyUsageFlags, LocalDate validTo) {
        validate(issuerSN, subjectUsername, keyUsageFlags, validTo);
        X509Certificate cert = generateCertificate();
    
        return exportGeneratedCertificate(cert);
    }

    private Certificate exportGeneratedCertificate(X509Certificate cert) {
        return new Certificate();
    }

    private void validate(String issuerSN, String subjectUsername, String keyUsageFlags, LocalDate validTo) {
    }
    private X509Certificate generateCertificate(){
//        try {
//            // Posto klasa za generisanje sertifikata ne moze da primi direktno privatni kljuc pravi se builder za objekat
//            // Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
//            // Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifikata
//
//            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
//
//            // Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
//            builder = builder.setProvider("BC");
//
//            // Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
//            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
//
//            // Postavljaju se podaci za generisanje sertifikata
//            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
//                    issuerData.getX500name(),
//                    new BigInteger(subjectData.getSerialNumber()),
//                    subjectData.getStartDate(),
//                    subjectData.getEndDate(),
//                    subjectData.getX500name(),
//                    subjectData.getPublicKey());
//
//            // Generise se sertifikat
//            X509CertificateHolder certHolder = certGen.build(contentSigner);
//
//            // Builder generise sertifikat kao objekat klase X509CertificateHolder
//            // Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
//            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
//            certConverter = certConverter.setProvider("BC");
//
//            // Konvertuje objekat u sertifikat
//            return certConverter.getCertificate(certHolder);
//        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
