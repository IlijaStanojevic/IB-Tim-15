package com.example.IBBackend.model;

import com.example.IBBackend.dto.CertificateContract;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requests")
public class CertificateRequest {
    private CertificateContract contract;
    private String requester;

    public CertificateRequest() {
    }

    public CertificateContract getContract() {
        return contract;
    }

    public void setContract(CertificateContract contract) {
        this.contract = contract;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }
    public boolean isSelfSigned(){
        return contract.getIssuerSN().isEmpty();
    }
    public Certificate.CertificateType getType(){
        if (isSelfSigned()){
            if (contract.getKeyUsageFlags().contains("3")){
                return Certificate.CertificateType.Root;
            }else{
                return Certificate.CertificateType.Intermediate;
            }
        }
        else{
            return Certificate.CertificateType.End;
        }
    }
    public void getOwnerOfContractIssuer(){

    }
}
