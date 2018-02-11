import { Injectable } from '@angular/core';
import { BaseComponent } from '../components/base/base.component';
import { CanActivate, Router } from '@angular/router';

@Injectable()
export class AuthGuardService extends BaseComponent implements CanActivate {

  constructor(public router: Router) {
    super();
  }

  canActivate(): boolean {
    if (!this.isLogged() || this.currentUser().token.hasExpired()) {
      this.logout();
      this.navigateTo('/');
      return false;
    }

    return true;
  }
}
