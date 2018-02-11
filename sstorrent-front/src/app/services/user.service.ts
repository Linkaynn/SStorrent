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

  public getAllMirrors(token) : Promise<any> {
    let url : string = `${environment.base_url}/${token}/retrieveAllMirrors`;
    return this.http.get(url, {headers: this.basicHeader}).toPromise();
  }

  public updateProfile(name, newPassword, mirrors : string[], token) : Promise<any>{
    let url : string = `${environment.base_url}/${token}/updateProfile`;
    return this.http.post(url, `name=${name}&newPassword=${newPassword}&mirrors=${mirrors.join(',')}`, {headers: this.formHeader}).toPromise();
  }

  public sendRequest(username, name, email, message) : Promise<any> {
    let url : string = `${environment.base_url}/sendRequest`;
    return this.http.post(url, `username=${username}&name=${name}&email=${email}&message=${message}`, {headers: this.formHeader}).toPromise();
  }

}
