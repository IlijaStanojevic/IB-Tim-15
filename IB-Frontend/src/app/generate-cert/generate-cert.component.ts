import { Component } from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-generate-cert',
  templateUrl: './generate-cert.component.html',
  styleUrls: ['./generate-cert.component.css']
})
export class GenerateCertComponent {
  form!: FormGroup;

  onSubmit() {

  }
}
