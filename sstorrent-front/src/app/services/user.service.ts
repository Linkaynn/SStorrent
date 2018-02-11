import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { environment } from '../../environments/environment';

@Injectable()
export class UserService {

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

  public login(username, password) : Promise<any> {
    let url : string = environment.base_url + "/login"
    return this.http.post(url, `username=${username}&password=${password}`, {headers: this.formHeader}).toPromise();
  }

  public getProfile(token) : Promise<any> {
    let url : string = `${environment.base_url}/${token}/me`;
    return this.http.get(url, {headers: this.basicHeader}).toPromise();
  }

  public getMirrors(token) : Promise<any> {
    let url : string = `${environment.base_url}/${token}/retrieveMirrors`;
    return this.http.get(url, {headers: this.basicHeader}).toPromise();
  }

}
