import { Injectable } from '@angular/core';
import {ApiServiceService} from "./api-service.service";
import {ConfigServiceService} from "./config-service.service";
import {HttpHeaders} from "@angular/common/http";



@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private apiService: ApiServiceService, private config: ConfigServiceService) {

  }
  getAllCertificates(){
    return this.apiService.get(this.config.getAllCertificates());
  }
  requestCertificate(){
    return this.apiService.get(this.config.requestCertificate());
  }
  validateCertificateInput(serialNumber: string){
    return this.apiService.get(this.config.validateCertificateInput(serialNumber));
  }
  validateCertificateUpload(file: File){
    const fileUploadRequest: FormData = new FormData();
    let httpheader: any;
    httpheader = new HttpHeaders({
      'Accept': 'multipart/form-data',
      'Content-Type': 'multipart/form-data'
    });
    fileUploadRequest.append('file', file, file.name);
    console.log(file);
    return this.apiService.post(this.config.validateCertificateUpload(), fileUploadRequest, httpheader);
  }
}
