package com.example.ibbackend.service;

import com.example.ibbackend.model.Certificate;
import com.example.ibbackend.model.User;
import com.example.ibbackend.repository.CertificateRepository;
import com.example.ibbackend.repository.UserRepository;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class CertificateGeneratorService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    // TODO fix path
    private static final String certDir = "\\src\\main\\resources\\certs\\";

    private Certificate issuer;
    private User subject;
    private KeyUsage flags;
    private boolean isAuthority;
    private X509Certificate issuerCertificate;
    private PrivateKey issuerPrivateKey;
    private LocalDateTime validTo;
    private KeyPair currentRSA;

    public boolean validateCertificate(String serialNumber) {

        Certificate cert = certificateRepository.findCertificateBySerialNumber(serialNumber).get();

        if (cert.getType() == Certificate.CertificateType.Root) {
            return true;
        } else {
            if (cert.isValid() && cert.checkDate(LocalDate.now())) {
                return validateCertificate(cert.getIssuer());
            } else {
                return false;
            }
        }
    }

    public Certificate issueCertificate(String issuerSN, String subjectUsername, String keyUsageFlags, LocalDateTime validTo) throws Exception {
        isAuthority = false;
        validate(issuerSN, subjectUsername, keyUsageFlags, validTo);
        X509Certificate cert = generateCertificate();

        return exportGeneratedCertificate(cert);
    }

    private Certificate exportGeneratedCertificate(X509Certificate cert) throws IOException, CertificateEncodingException {
        Certificate certForDB = certForDBFromX509(cert);
        certificateRepository.save(certForDB);

        FileOutputStream fosC = new FileOutputStream(System.getProperty("user.dir") + certDir + certForDB.getSerialNumber() + ".crt");
        fosC.write(cert.getEncoded());
        fosC.close();
        FileOutputStream fosK = new FileOutputStream(System.getProperty("user.dir") + certDir + certForDB.getSerialNumber() + ".key");
        fosK.write(currentRSA.getPrivate().getEncoded());
        fosK.close();

        return certForDB;
    }

    private Certificate certForDBFromX509(X509Certificate cert) {
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
        if (!issuerSN.isEmpty()) {
            Optional<Certificate> issuerOptional = certificateRepository.findCertificateBySerialNumber(issuerSN);
            if (issuerOptional.isPresent()) {
                issuer = issuerOptional.get();
                FileInputStream inputStreamCert = new FileInputStream(System.getProperty("user.dir") + certDir + "/" + issuerSN + ".crt");
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                issuerCertificate = (X509Certificate) certificateFactory.generateCertificate(inputStreamCert);

                byte[] keyBytes = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + certDir + issuerSN + ".key"));
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                issuerPrivateKey = keyFactory.generatePrivate(keySpec);

            } else {
                throw new Exception("Certificate " + issuerSN + " not found");
            }
        }

        if (validTo.isBefore(LocalDateTime.now())) {
            throw new Exception("Date is not valid");
        }
        if (!issuerSN.isEmpty()) {
            issuerCertificate.checkValidity();
        }

        this.validTo = validTo;
        Optional<User> userOptional = userRepository.findUserByEmail(subjectUsername);
        if (userOptional.isPresent()) {
            subject = userOptional.get();
        } else {
            throw new Exception("No user with that email");
        }
        flags = ParseFlags(keyUsageFlags);
    }

    private KeyUsage ParseFlags(String keyUsageFlags) throws Exception {
        if (keyUsageFlags == null || keyUsageFlags.isEmpty()) {
            throw new Exception("KeyUsageFlags are mandatory");
        }

        String[] flagArray = keyUsageFlags.split(",");
        int retVal = 0;
        int[] possibleElements = {1, 2, 4, 8, 16, 32, 64, 128, 32768};
        for (String flag : flagArray) {
            try {
                int index = Integer.parseInt(flag);
                int currentFlag = possibleElements[index];
                retVal |= currentFlag;
                if (currentFlag == KeyUsage.keyCertSign) {
                    isAuthority = true;
                }
            } catch (NumberFormatException e) {
                throw new Exception("Unknown flag: " + flag);
            }

        }


        return new KeyUsage(retVal);
    }


    private X509Certificate generateCertificate() throws NoSuchAlgorithmException, OperatorCreationException, CertificateException, CertIOException {
        String subjectText = "CN=" + subject.getUsername();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        currentRSA = keyPair;
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        X500Principal subject = new X500Principal(subjectText);
        Instant instant = validTo.atZone(ZoneId.systemDefault()).toInstant();
        Date dateValidTo = Date.from(instant);

        UUID unique = UUID.randomUUID();
        long hi = unique.getMostSignificantBits();
        long lo = unique.getLeastSignificantBits();
        byte[] bytes = ByteBuffer.allocate(16).putLong(hi).putLong(lo).array();
        BigInteger big = new BigInteger(bytes).abs();

        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuerCertificate != null ? new JcaX509CertificateHolder(issuerCertificate).getSubject() : new X500Name(subject.getName())
                , big, new Date(),
                dateValidTo, new X500Name(subject.getName()), publicKey);
//        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(new X500Name(subject.getName()), big, new Date(),
//                dateValidTo, new X500Name(subject.getName()), publicKey);

        if (issuerCertificate == null) {
            certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(isAuthority));
        } else {
            certBuilder.addExtension(Extension.authorityKeyIdentifier, false, issuerCertificate.getSubjectX500Principal().getEncoded());
            certBuilder.addExtension(Extension.subjectKeyIdentifier, false, publicKey.getEncoded());
            certBuilder.addExtension(Extension.keyUsage, false, new KeyUsage(flags.getPadBits()));
        }
        ContentSigner signer;
        if (issuerCertificate == null) {
            signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider(new BouncyCastleProvider()).build(privateKey);
        } else {
            signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider(new BouncyCastleProvider()).build(issuerPrivateKey);
        }

        X509CertificateHolder certHolder = certBuilder.build(signer);
        X509Certificate generatedCertificate = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider())
                .getCertificate(certHolder);
        return generatedCertificate;
    }
}
