import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/models/user';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  user:User;
  isConfirm:boolean = false;
  showAlert:boolean = false;
  alertMessage:string = '';
  code:string;

  constructor(private http:HttpClient){
    
    }

  ngOnInit(): void {
    this.user = {email:"",password:"",name:"",surname:"",phoneNum:"",isEnabled:false,showPassword:false};
    this.isConfirm = false;
    this.user.showPassword = false;
  }

  public createUser(){
    this.signUp().subscribe((res) => {
      if(res == "Successful sign up!"){
        this.isConfirm = true;
      }
    })
  }

  public signUp(): Observable<any> {
      let url = "http://localhost:8080/api/user/signup";
    const options: any = {
      responseType: 'text',
    };
    return this.http.post<string>(url, {email: this.user.email, password: this.user.password, name: this.user.name, surname : this.user.surname, phoneNum : this.user.phoneNum}, options);
    }

    public activateUser(){
      this.confirmSignUp().subscribe((res) => {
        if(res == "Account verified!"){
          this.alertMessage = "Account activated successfully!";
          this.showAlert = true;
        }else{
          this.alertMessage = "Wrong code, try again!";
          this.showAlert = true;
        }
      })
    }

  confirmSignUp(): Observable<any> {
    let url = "http://localhost:8080/api/user/activate";
    const options: any = {
      responseType: 'text',
    };
    console.log(this.code);
    return this.http.post<string>(url, {email: this.user.email, activationCode: this.code}, options);
  }
}