import { Injectable } from '@angular/core';
import {HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {ConfigServiceService} from "./config-service.service";
import {UserService} from "./user.service";
import {ApiServiceService} from "./api-service.service";
import { map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor(
    private apiService: ApiServiceService,
    private userService: UserService,
    private config: ConfigServiceService,
    private router: Router
  ) {
  }

  public access_token = null;

  login(user:any) {
    const loginHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    // const body = `email=${user.email}&password=${user.password}`;
    const body = {
      'email': user.email,
      'password': user.password
    };
    return this.apiService.post(this.config.login_url, JSON.stringify(body), loginHeaders)
      .pipe(map((res) => {
        console.log('Login success');
        this.access_token = res.body.accessToken;
        localStorage.setItem("jwt", res.body.accessToken);
        localStorage.setItem("role", res.body.role);
        localStorage.setItem("userId", res.body.userId)
      }));
  }

  signup(user:any) {
    const signupHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    return this.apiService.post(this.config.signup_url, JSON.stringify(user), signupHeaders)
      .pipe(map(() => {
        console.log('Sign up success');
      }));
  }

  signup_driver(user:any) {
    const signupHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    return this.apiService.post(this.config.driver_signup_url, JSON.stringify(user), signupHeaders)
      .pipe(map(() => {
        console.log('Sign up success');
      }));
  }

  logout() {
    this.userService.currentUser = null;
    localStorage.removeItem("jwt");
    localStorage.removeItem("role");
    this.access_token = null;
    this.router.navigate(['/login']);
  }

  tokenIsPresent() {
    const token = localStorage.getItem("jwt");

    return token != null;
  }

  getToken() {
    const token = localStorage.getItem("jwt");

    return token;
  }
}
