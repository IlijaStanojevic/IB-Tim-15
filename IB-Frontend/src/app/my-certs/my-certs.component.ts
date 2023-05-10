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

    // Set the response type to 'blob' for downloading files
    const options = { responseType: 'blob' as 'json' };

    this.http.get(url, options).subscribe((data: any) => {
      // Create a Blob from the response data
      const blob = new Blob([data], { type: 'application/octet-stream' });

      // Generate a unique file name for the downloaded file
      const fileName = `${serialNumber}.key`;

      // Create a temporary link element
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = fileName;

      // Programmatically click the link to trigger the download
      link.click();

      // Clean up the temporary link
      window.URL.revokeObjectURL(link.href);
      link.remove();
    });
  }
}
