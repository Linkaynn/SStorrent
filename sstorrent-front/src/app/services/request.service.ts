import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { environment } from '../../environments/environment';

@Injectable()
export class RequestService {

  private formHeader: Headers = new Headers(
    {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  );

  private basicHeader: Headers = new Headers(
    {
      'Content-Type': 'application/json'
    }
  );

  constructor(private http: Http) {}

  public getRequests(token) : Promise<any> {
    let url : string = `${environment.base_url}/${token}/getRequests`;
    return this.http.get(url, {headers: this.basicHeader}).toPromise();
  }

  public rejectRequest(request, token) : Promise<any> {
    let url : string = `${environment.base_url}/${token}/rejectRequest`;
    return this.http.post(url, `requestId=${request.id}`, {headers: this.formHeader}).toPromise();
  }

  public acceptRequest(request, token) : Promise<any> {
    let url : string = `${environment.base_url}/${token}/acceptRequest`;
    return this.http.post(url, `requestId=${request.id}`, {headers: this.formHeader}).toPromise();
  }

}
