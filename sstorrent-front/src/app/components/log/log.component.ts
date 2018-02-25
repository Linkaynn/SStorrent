import { Component, OnInit } from '@angular/core';
import { BaseComponent } from '../base/base.component';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.scss']
})
export class LogComponent extends BaseComponent {

  logs : any[];

  constructor(private adminService : AdminService) {
    super();

    this.startLoading();
    this.adminService.getLogs(this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error happen retrieving de logs.")
      } else {
        this.logs = json.data.logs;
      }

    }).catch((err) => {
      this.stopLoading();
      console.error(err);
      this.error("Internal. An error happen retrieving de logs.")
    })
  }

}
