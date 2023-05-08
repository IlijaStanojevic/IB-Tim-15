import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {RegisterComponent} from './register/register.component';
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {TokenInterceptor} from "./interceptor/TokenInterceptor";
import {AuthServiceService} from "./auth-service.service";
import {ConfigServiceService} from "./config-service.service";
import {UserService} from "./user.service";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
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
