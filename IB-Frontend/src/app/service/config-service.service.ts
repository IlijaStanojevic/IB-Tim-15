import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigServiceService {
  private _api_url = 'https://localhost:8080/api';
  private _auth_url = 'https://localhost:8080/api/user';
  private _user_url = this._api_url + '/user';

  private _certs_url = 'https://localhost:8080/api/certs'
  private _login_url = this._auth_url + '/login';
  private _driver_url = this._api_url + '/driver/'

  get login_url(): string {
    return this._login_url;
  }

  private _whoami_url = this._user_url + '/whoami';

  get whoami_url(): string {
    return this._whoami_url;
  }

  private _users_url = this._user_url;

  get users_url(): string {
    return this._users_url;
  }

  private _signup_url = this._auth_url + '/signup';

  get signup_url(): string {
    return this._signup_url;
  }
  getAllCertificates(){
    return this._certs_url;
  }
  getMyCertificates(){
    return this._certs_url + "/mycerts";
  }
  requestCertificate(){
    return this._certs_url + "/request";
  }
  validateCertificateInput(serialNumber: string){
    return this._certs_url + "/" + serialNumber + "/validate"
  }
  validateCertificateUpload(){
    return this._certs_url + "/validate" + "/upload"
  }

  getRequests(){
    return this._certs_url + "/requests"
  }


}
