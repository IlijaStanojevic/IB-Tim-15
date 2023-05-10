import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {
  email:String;
  password:String;
  isConfirm:boolean = false;
  showAlert:boolean = false;
  newPassword:boolean = false;
  alertMessage:string = '';
  code:string;

  constructor(private http:HttpClient, private router: Router){
    
  }

  public createUser(){
    this.signUp().subscribe((res) => {
      if(res == "E-mail sent!"){
        this.isConfirm = true;
      }
    })
  }

  public signUp(): Observable<any> {
    let url = "http://localhost:8080/api/user/resetPasswordInit";
  const options: any = {
    responseType: 'text',
  };
  return this.http.post<string>(url, {email: this.email, password: ""}, options);
  }

  public activateUser(){
    this.confirmSignUp().subscribe((res) => {
      if(res == "Code is correct!"){
        this.newPassword = true;
      }else{
        this.alertMessage = "Wrong code, try again!";
        this.showAlert = true;
      }
    })
  }

  confirmSignUp(): Observable<any> {
    let url = "http://localhost:8080/api/user/checkCode";
    const options: any = {
      responseType: 'text',
    };
    console.log(this.code);
    return this.http.post<string>(url, {email: this.email, activationCode: this.code}, options);
  }



  public newPasswordMethod(){
    this.confirmNewPassword().subscribe((res) => {
      if(res == "Password resetted!"){
        this.alertMessage = "Password resetted successfully!";
        this.showAlert = true;
        this.router.navigate(["/login"]);
      }else{
        this.alertMessage = "Something went wrong, try again!";
        this.showAlert = true;
      }
    })
  }

confirmNewPassword(): Observable<any> {
  let url = "http://localhost:8080/api/user/resetPassword";
  const options: any = {
    responseType: 'text',
  };
  return this.http.post<string>(url, {email: this.email, password: this.password}, options);
}
}
