import { Injectable } from '@angular/core';
import {ApiServiceService} from "./api-service.service";
import {ConfigServiceService} from "./config-service.service";
import { map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  currentUser!:any;

  constructor(
    private apiService: ApiServiceService,
    private config: ConfigServiceService
  ) {
  }

  getMyInfo() {
    return this.apiService.get(this.config.whoami_url)
      .pipe(map(user => {
        this.currentUser = user;
        console.log(user);
        return user;
      }));
  }
}
