package com.example.IBBackend.service;

import com.example.IBBackend.model.Certificate;
import com.example.IBBackend.model.User;
import com.example.IBBackend.repository.CertificateRepository;
import com.example.IBBackend.repository.UserRepository;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class CertificateGeneratorService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    private static String certDir = "certs";

    private Certificate issuer;
    private User subject;
    private KeyUsage flags;
    private boolean isAuthority;
    private X509Certificate issuerCertificate;
    private LocalDateTime validTo;
//    private RSA currentRSA;


    public Certificate issueCertificate(String issuerSN, String subjectUsername, String keyUsageFlags, LocalDateTime validTo) throws Exception {
        validate(issuerSN, subjectUsername, keyUsageFlags, validTo);
        X509Certificate cert = generateCertificate();

        return exportGeneratedCertificate(cert);
    }

    private Certificate exportGeneratedCertificate(X509Certificate cert) {
        Certificate certForDB = certForDBFromX509(cert);
        certificateRepository.save(certForDB);
        return certForDB;
    }
    private Certificate certForDBFromX509(X509Certificate cert){
        Certificate certForDB = new Certificate();
        certForDB.setIssuer(issuer != null ? issuer.getSerialNumber() : null);
        certForDB.setValid(true);
        certForDB.setType(isAuthority
                ? (issuerCertificate == null ? Certificate.CertificateType.Root : Certificate.CertificateType.Intermediate)
                : Certificate.CertificateType.End);
        certForDB.setSerialNumber(cert.getSerialNumber().toString());
        certForDB.setUsername(subject.getUsername());
        certForDB.setValidFrom(cert.getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        certForDB.setValidTo(cert.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        return certForDB;
    }
    private void validate(String issuerSN, String subjectUsername, String keyUsageFlags, LocalDateTime validTo) throws Exception {
//        if ( !issuerSN.isEmpty()){
//            Optional<Certificate> issuerOptional = certificateRepository.findCertificateBySerialNumber(issuerSN);
//            if ( issuerOptional.isPresent()){
//                issuer = issuerOptional.get();
//                FileInputStream inputStream = new FileInputStream(certDir + "/" + issuerSN + ".crt");
//                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//                X509Certificate cert = (X509Certificate)certificateFactory.generateCertificate(inputStream);
//                // TODO  RSA private key read
//
//            }else{
//                throw new Exception("Certificate "+ issuerSN + " not found");
//            }
        // TODO valid from and to
//        if (!(validTo > DateTime.Now && (string.IsNullOrEmpty(issuerSN) || validTo < issuerCertificate.NotAfter))){
//            System.Console.WriteLine($"Comparing {validTo} and {DateTime.Now}");
//            throw new Exception("The date is not in the accepted range");
//        }
        this.validTo = validTo;
        Optional<User> userOptional =  userRepository.findUserByEmail(subjectUsername);
        if (userOptional.isPresent()){
            subject = userOptional.get();
        }else{
            throw  new Exception("No user with that email");
        }
    }


    private X509Certificate generateCertificate() throws NoSuchAlgorithmException, OperatorCreationException, CertificateException, CertIOException {
        String subjectText = "CN=" + subject.getUsername();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        X500Principal subject = new X500Principal(subjectText);
        Instant instant = validTo.atZone(ZoneId.systemDefault()).toInstant();
        Date dateValidTo = Date.from(instant);


        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(new X500Name(subject.getName()), BigInteger.ONE, new Date(),
                dateValidTo, new X500Name(subject.getName()), publicKey);
//        if (issuerCertificate == null) {
//            certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(isAuthority));
//        } else {
//            certBuilder.addExtension(Extension.authorityKeyIdentifier, false, issuerCertificate.getSubjectX500Principal());
//            certBuilder.addExtension(Extension.subjectKeyIdentifier, false, publicKey);
//            certBuilder.addExtension(Extension.keyUsage, false, new KeyUsage(flags));
//        }
        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(isAuthority));
//        certBuilder.addExtension(Extension.authorityKeyIdentifier, false, issuerCertificate.getSubjectX500Principal().getEncoded());
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, publicKey.getEncoded());
        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider(new BouncyCastleProvider()).build(privateKey);
        X509CertificateHolder certHolder = certBuilder.build(signer);
        X509Certificate generatedCertificate = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider())
                .getCertificate(certHolder);
        return generatedCertificate;
    }
}
