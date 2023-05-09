import {Component, OnInit} from '@angular/core';
import {CertificateService} from "../service/certificate.service";
import {config, firstValueFrom} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {resolve} from "@angular/compiler-cli";
import {logMessages} from "@angular-devkit/build-angular/src/builders/browser-esbuild/esbuild";

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
  protected certificates: Certificate[] = [];
  validateInput!: FormGroup;
  public validateResponseMessage: String = '';
  public selectedFile: File | null = null;
  public serialNumber: string = '';

  constructor(private certificateService: CertificateService, private formBuilder: FormBuilder, private http: HttpClient) {

  }

  async loadCertificates() {
    const response = await firstValueFrom(this.certificateService.getAllCertificates()).then((response) => {
      // console.log(response);
      return response;
    }).catch((error => {
      console.log(error);

    }))
    if (!!response) {
      this.certificates = response;
    }
  }

  ngOnInit() {
    this.loadCertificates();
    this.validateInput = this.formBuilder.group({
      serialNumber: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])]
    });

  }

  async onValidateInput() {
    this.http.get("http://localhost:8080/api/certs/" + this.serialNumber + "/validate", {responseType: 'text'})
      .subscribe(
        response => {
          this.validateResponseMessage = response;
        },
        error => {
          this.validateResponseMessage = error.error;

        }
      );
  }

  onValidateUpload() {
    if (this.selectedFile) {
      const fileUploadRequest: FormData = new FormData();
      fileUploadRequest.append('file', this.selectedFile);
      this.http.post("http://localhost:8080/api/certs/validate/upload", fileUploadRequest, {responseType: 'text'})
        .subscribe(
          response => {
            this.validateResponseMessage = response;
          },
          error => {
            console.log(error);
            this.validateResponseMessage = error.error;

          }
        );

    }
  }

  handleFileUpload(event: any) {
    this.selectedFile = event.target.files[0] as File;
  }
}
