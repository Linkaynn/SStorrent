import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

import { BaseComponent } from '../base/base.component';

import { UserService } from '../../services/user.service';
import { SearchService } from '../../services/search.service';
import { ActivatedRoute } from '@angular/router';
import { TorrentModalComponent } from '../torrent-modal/torrent-modal.component';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent extends BaseComponent {

  torrents = [];

  value : string;
  mirror : string = "all";

  searchForm: FormGroup;

  constructor(private userService : UserService, private fb : FormBuilder, private searchService : SearchService, private route: ActivatedRoute) {
    super();

    this.retrieveMirrors();

    this.searchForm = fb.group({
      value: ['', Validators.required],
      mirror: ['', Validators.required]
    })
  }

  retrieveMirrors() {
    this.startLoading();
    this.userService.getMirrors(this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error was happen retrieving your mirrors. Probably your session expire.")
        this.logout();
      } else {
        this.currentUser().setMirrors(json.data.mirrors);
        this.mirror = this.currentUser().mirrors[0];

        const value = this.route.snapshot.paramMap.get('value');

        if (value != null) {
          this.value = value;
        }

      }
    }).catch((err) => {
      this.stopLoading();
      console.error(err);
      this.error("An error was happen retrieving your mirrors. Reload page please.")
    })
  }

  search() {
    if (this.isValidSearch()) {
      this.startLoading();

      this.searchService.search(this.mirror, this.value.trim(), this.currentUser().token.token).then((response) => {
        this.stopLoading();

        let json = response.json();

        if (json.status == "error") {
          this.error(`Error searching ${this.value}, try again later.`)
        } else {
          let lightTorrents = json.data.lightTorrents;

          if (lightTorrents) {
            if (lightTorrents.length == 0) {
              this.warning("No torrents found with value " + this.value.trim());
            }

            this.torrents = lightTorrents;
          }
        }
      }).catch((err) => {
        this.stopLoading();
        console.error(err);
        this.error(`Internal. Error searching ${this.value}, try again later.`)
      })
    }
  }

  isValidSearch() {
    return this.searchForm.valid && this.value.trim().length > 0;
  }

  retrieveLink(torrent) {
    const modal = this.openModal(TorrentModalComponent);
    modal.componentInstance.mirror = this.mirror;
    modal.componentInstance.torrent = torrent;
  }

}
