import { Injectable } from '@angular/core';
import {ApiServiceService} from "./api-service.service";
import {ConfigServiceService} from "./config-service.service";



@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private apiService: ApiServiceService, private config: ConfigServiceService) {

  }
  getAllCertificates(){
    return this.apiService.get(this.config.getAllCertificates());
  }
}
