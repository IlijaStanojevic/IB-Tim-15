import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigServiceService {
  private _api_url = 'http://localhost:8080/api';
  private _auth_url = 'http://localhost:8080/api/user';
  private _user_url = this._api_url + '/user';
  private _driver_signup_url = 'http://localhost:8080/api/driver';

  get driver_signup_url(): string{
    return this._driver_signup_url
  }
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


}
