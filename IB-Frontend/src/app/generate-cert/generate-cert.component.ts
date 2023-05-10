import {Component} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {CertificateService} from "../service/certificate.service";
import {UserService} from "../service/user.service";
import {compareSegments} from "@angular/compiler-cli/src/ngtsc/sourcemaps/src/segment_marker";

@Component({
  selector: 'app-generate-cert',
  templateUrl: './generate-cert.component.html',
  styleUrls: ['./generate-cert.component.css']
})
export class GenerateCertComponent {
  issuer: any;
  date: any;


  constructor(private certificateService: CertificateService, private userService: UserService) {
  }

  loadFlags() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    const selectedCheckBoxes: number[] = [];
    checkboxes.forEach(function (checkbox, index) {
      const inputCheckbox = checkbox as HTMLInputElement;
      if (inputCheckbox.checked) {
        selectedCheckBoxes.push(index + 1);
      }
    });
    return selectedCheckBoxes;
  }

  onSubmit() {
    console.log(this.loadFlags());
    let email;
    this.userService.getMyInfo().subscribe(response => {
      email = response['email'];
      const dateDate = new Date(this.date);
      const request = {
        "contract": {
          "subjectEmail": email,
          "keyUsageFlags": this.loadFlags().toString(),
          "issuerSN": this.issuer,
          "date": dateDate.toISOString()
        },
        "requester": email
      }
      console.log(request);
      this.certificateService.requestCertificate(request).subscribe(response => {
        console.log(response);
      },
        error => {
        console.log(error);
        })
    })


  }
}

