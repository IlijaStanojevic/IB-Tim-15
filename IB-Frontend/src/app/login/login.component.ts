import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../service/user.service";
import {AuthServiceService} from "../service/auth-service.service";
import {Subject} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  title = 'Login';
  form!: FormGroup;
  constructor(
    private userService: UserService,
    private authService: AuthServiceService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder

  ) { }
  submitted = false;
  returnUrl!: string;
  ngOnInit(){
    this.form = this.formBuilder.group({
      email: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])]
    });
  }
  onSubmit() {
    console.log(this.form.value)
    this.authService.login(this.form.value)
      .subscribe(data => {
        console.log(localStorage.getItem("jwt"));
        this.router.navigate([""])
      });
  }
}
