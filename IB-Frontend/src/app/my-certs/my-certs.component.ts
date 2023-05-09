import {Component, OnInit} from '@angular/core';
import {Certificate} from "../all-certs/all-certs.component";
import {CertificateService} from "../service/certificate.service";
import {Validators} from "@angular/forms";
import {firstValueFrom} from "rxjs";

@Component({
  selector: 'app-my-certs',
  templateUrl: './my-certs.component.html',
  styleUrls: ['./my-certs.component.css']
})
export class MyCertsComponent implements  OnInit{
  certificates: Certificate[] = [];
  constructor(private certificateService: CertificateService) {
  }
  ngOnInit() {
    this.loadCertificates();

  }

  private async loadCertificates() {
    const response = await firstValueFrom(this.certificateService.getMyCertificates()).then((response) => {
      // console.log(response);
      return response;
    }).catch((error => {
      console.log(error);

    }))
    if (!!response) {
      this.certificates = response;
    }
  }
}
