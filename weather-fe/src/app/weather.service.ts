import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  private REST_API_SERVER = 'http://localhost:8080/';
  constructor(private httpClient: HttpClient) { }
   public getData(id: string) {
    return this.httpClient.get(this.REST_API_SERVER + id);
  }
}
