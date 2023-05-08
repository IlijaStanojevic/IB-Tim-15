import {Component, OnInit} from '@angular/core';
import {CertificateService} from "../service/certificate.service";
import {firstValueFrom} from "rxjs";

export interface Certificate {
  serialNumber: string;
  issuer: string;
  validFrom: string;
  validTo: string;
  isValid: boolean;
  type: CertificateType;
  owner: string;
}

export enum CertificateType {
  Root = 'Root',
  Intermediate = 'Intermediate',
  End = 'End',
}

@Component({
  selector: 'app-all-certs',
  templateUrl: './all-certs.component.html',
  styleUrls: ['./all-certs.component.css']
})
export class AllCertsComponent implements OnInit {
  protected  certificates: Certificate[] = [];
  constructor(private certificateService: CertificateService) {

  }

  async loadCertificates(){
    const response = await firstValueFrom(this.certificateService.getAllCertificates()).then((response) => {
      console.log(response);
      return response;
    }).catch((error =>{
      console.log(error);

    }))
    if (!!response){
      this.certificates = response;
    }
  }
  ngOnInit() {
    this.loadCertificates();
  }
}
