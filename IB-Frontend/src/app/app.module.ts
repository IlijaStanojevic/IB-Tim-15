import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {RegisterComponent} from './register/register.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {TokenInterceptor} from "./interceptor/TokenInterceptor";
import {AuthServiceService} from "./service/auth-service.service";
import {ConfigServiceService} from "./service/config-service.service";
import {UserService} from "./service/user.service";
import {ReactiveFormsModule} from "@angular/forms";
import { GenerateCertComponent } from './generate-cert/generate-cert.component';
import { RequestsComponent } from './requests/requests.component';
import { MyCertsComponent } from './my-certs/my-certs.component';
import { AllCertsComponent } from './all-certs/all-certs.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    GenerateCertComponent,
    RequestsComponent,
    MyCertsComponent,
    AllCertsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    AuthServiceService,
    AuthServiceService,
    ConfigServiceService,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
