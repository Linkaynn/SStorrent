import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

import { BaseComponent } from '../base/base.component';

import { UserService } from '../../services/user.service';
import { SearchService } from '../../services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent extends BaseComponent {

  private torrents = [];

  private value : string;
  private mirror : string = "all";

  searchForm: FormGroup;

  constructor(private userService : UserService, private fb : FormBuilder, private searchService : SearchService) {
    super();

    this.startLoading();
    this.userService.getMirrors(this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error was happend retrieving your mirrors. Probably your session expire.")
        this.logout();
      } else {
        this.currentUser().setMirrors(json.data.mirrors);
        this.mirror = this.currentUser().mirrors[0];
      }
    }).catch((err) => {
      this.stopLoading();
      console.error(err);
      this.error("An error was happend retrieving your mirrors. Reload page please.")
    })


    this.searchForm = fb.group({
      value: ['', Validators.required],
      mirror: ['', Validators.required]
    })
  }

  search() {
    this.startLoading();

    this.searchService.search(this.mirror, this.value, this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        if (this.currentUser().token.hasExpired()) {
          this.logout();
          this.error("Your session expired.")
        } else {
          this.error(`Error searching ${this.value}, try again later.`)
        }
      } else {
        if (json.data.lightTorrents) {
          this.torrents = json.data.lightTorrents;
        }
      }
    }).catch((err) => {
      this.stopLoading();
      console.error(err);
    })
  }

  retrieveLink(url) {
    console.log(this.mirror + " - " + url);
  }

}
