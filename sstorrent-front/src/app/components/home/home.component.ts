import { Component, OnInit } from '@angular/core';
import { BaseComponent } from '../base/base.component';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent extends BaseComponent {

  searches : string[];

  constructor(private userService : UserService) {
    super();

    this.startLoading();
    this.userService.getProfile(this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error was happen retrieving your profile. Probably your session expire.")
        this.logout();
      } else {
        this.searches = json.data.searches;
      }
    }).catch((err) => {
      this.stopLoading();
      console.error(err);
    })
  }

}
