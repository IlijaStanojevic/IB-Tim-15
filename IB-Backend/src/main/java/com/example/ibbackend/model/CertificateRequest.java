package com.example.ibbackend.model;

import com.example.ibbackend.dto.CertificateContract;
import com.example.ibbackend.model.Certificate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requests")
public class CertificateRequest {
    @Id
    private String id;
    private CertificateContract contract;
    private String requester;
    private String message;
    private RequestState state;
    private Certificate.CertificateType type;
    private String userWhoDecides;





    public String getUserWhoDecides() {
        return userWhoDecides;
    }

    public void setUserWhoDecides(String userWhoDecides) {
        this.userWhoDecides = userWhoDecides;
    }

    public enum RequestState{
        ACCEPTED, DECLINED, PENDING
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public Certificate.CertificateType getType() {
        return type;
    }

    public void setType(Certificate.CertificateType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
    public Certificate.CertificateType calculateType(){
        if (contract.getKeyUsageFlags().contains("2")){
            if (isSelfSigned()){
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
