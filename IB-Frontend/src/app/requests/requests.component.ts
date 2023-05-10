import { Component, OnInit } from '@angular/core';
import { request } from '../models/request';
import { RequestService } from '../service/request.service';
import { HttpClient } from '@angular/common/http';
import {firstValueFrom} from "rxjs";

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit{
  protected requests: request[] = [];
  public validateResponseMessage: String = '';

  constructor(private requestService: RequestService, private http: HttpClient) {

  }

  ngOnInit(): void {
    this.loadRequests();
  }

  async loadRequests() {
    const response = await firstValueFrom(this.requestService.getRequests()).then((response) => {
      // console.log(response);
      return response;
    }).catch((error => {
      console.log(error);

    }))
    if (!!response) {
      this.requests = response;
    }
  }
}
