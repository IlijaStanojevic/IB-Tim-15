import { Injectable } from '@angular/core';
import {ApiServiceService} from "./api-service.service";
import {ConfigServiceService} from "./config-service.service";

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private apiService: ApiServiceService, private config: ConfigServiceService) {

  }
  getRequests(){
    return this.apiService.get(this.config.getRequests());
  }
}
