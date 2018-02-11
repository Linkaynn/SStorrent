import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { environment } from '../../environments/environment';

@Injectable()
export class SearchService {

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

  public search(mirror, value, token) : Promise<any> {
    let url : string = `${environment.base_url}/search/${mirror}/${value}?token=${token}`;
    return this.http.get(url, {headers: this.basicHeader}).toPromise();
  }

}
