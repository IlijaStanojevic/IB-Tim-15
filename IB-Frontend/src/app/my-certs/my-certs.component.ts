import {Component, OnInit} from '@angular/core';
import {Certificate} from "../all-certs/all-certs.component";
import {CertificateService} from "../service/certificate.service";
import {Validators} from "@angular/forms";
import {firstValueFrom} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-my-certs',
  templateUrl: './my-certs.component.html',
  styleUrls: ['./my-certs.component.css']
})
export class MyCertsComponent implements  OnInit{
  certificates: Certificate[] = [];
  constructor(private certificateService: CertificateService, private http: HttpClient) {
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

  downloadKey(serialNumber: string) {
    const url = `http://localhost:8080/api/keys/${serialNumber}/download`;

    const options = { responseType: 'blob' as 'json' };

    this.http.get(url, options).subscribe((data: any) => {
      const blob = new Blob([data], { type: 'application/octet-stream' });


      const fileName = `${serialNumber}.key`;

      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = fileName;

      link.click();

      window.URL.revokeObjectURL(link.href);
      link.remove();
    });
  }
}
