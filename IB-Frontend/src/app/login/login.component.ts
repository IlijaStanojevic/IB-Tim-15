import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthServiceService} from "../service/auth-service.service";
import { User } from '../models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  user:User | undefined;
  title = 'Login';
  showAlert: boolean = false;
  alertMessage: string = "";
  constructor(
    private authService: AuthServiceService,
    private router: Router,
  ) { }
  submitted = false;
  returnUrl!: string;
  ngOnInit(){
    this.user = {} as User;
  }
  onSubmit() {
    this.authService.login(this.user)
      .subscribe(data => {
        console.log(localStorage.getItem("jwt"));
        this.router.navigate([""])
      });
  }

  SignUp()
  {
    this.router.navigate(["/signup"]);
  }
}
