import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { BaseComponent } from '../base/base.component';

import { SearchService } from '../../services/search.service';

@Component({
  selector: 'app-torrent-modal',
  templateUrl: './torrent-modal.component.html',
  styleUrls: ['./torrent-modal.component.scss']
})
export class TorrentModalComponent extends BaseComponent {

  torrent = null;
  mirror = null;
  link = null;
  linkType = null;

  constructor(
    public activeModal: NgbActiveModal,
    private searchService : SearchService,
    private sanitization: DomSanitizer
  ) {
    super();
  }

  retrieveLink() {
    this.startLoading();
    this.searchService.retrieveLink(this.torrent.url, this.mirror, this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error happend retrieving the link of the torrent. Try again later.")
      } else {
        this.link = json.data.link;
        this.linkType = json.data.linkType;
      }
    }).catch((err) => {
      console.error(err);
      this.error("Internal. An error happend retrieving the link of the torrent. Try again later.")
      this.stopLoading();
    })
  }

}
