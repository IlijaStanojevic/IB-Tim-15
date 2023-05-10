import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./register/register.component";
import {AuthGuard} from "./auth.guard";
import {AllCertsComponent} from "./all-certs/all-certs.component";
import {GenerateCertComponent} from "./generate-cert/generate-cert.component";
import {RequestsComponent} from "./requests/requests.component";
import {MyCertsComponent} from "./my-certs/my-certs.component";
import { ResetPasswordComponent } from './reset-password/reset-password.component';

const routes: Routes = [{
  path: 'login',
  component: LoginComponent
},{
  path: "",
  component: HomeComponent,
  canActivate: [AuthGuard]
},{
  path: "reset-password",
  component: ResetPasswordComponent,
},{
  path: "signup",
  component: RegisterComponent
},{
  path: "certificates",
  component: AllCertsComponent,
  canActivate: [AuthGuard]
},
  {
    path: "generate",
    component: GenerateCertComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "requests",
    component: RequestsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "myCerts",
    component: MyCertsComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
