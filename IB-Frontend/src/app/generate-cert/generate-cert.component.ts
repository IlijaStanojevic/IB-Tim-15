import {Component} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {CertificateService} from "../service/certificate.service";

@Component({
  selector: 'app-generate-cert',
  templateUrl: './generate-cert.component.html',
  styleUrls: ['./generate-cert.component.css']
})
export class GenerateCertComponent {
  issuer: any;
  date: any;


  constructor(private certificateService: CertificateService) {
  }
  loadFlags() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    const selectedCheckBoxes: number[] = [];
    checkboxes.forEach(function(checkbox, index) {
      const inputCheckbox = checkbox as HTMLInputElement;
      if (inputCheckbox.checked) {
        selectedCheckBoxes.push(index+1);
      }
    });
    return selectedCheckBoxes;
  }
  onSubmit() {
    console.log(this.loadFlags());

    // const request = {
    //   "contract": {
    //     "subjectEmail":
    //   }
    // }
  }
}
