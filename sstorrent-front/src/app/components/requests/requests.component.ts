import { Component, OnInit } from '@angular/core';
import { BaseComponent } from '../base/base.component';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent extends BaseComponent {

  requests : any[];

  constructor(private adminService : AdminService) {
    super();

    this.startLoading();
    this.adminService.getRequests(this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error happen retrieving de requests.")
      } else {
        this.requests = json.data.requests;
      }

    }).catch((err) => {
      this.stopLoading();
      console.error(err);
      this.error("Internal. An error happen retrieving de requests.")
    })
  }

  reject(request) {
    this.startLoading();
    this.adminService.rejectRequest(request, this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error happen rejecting de requests.")
      } else {
        for (let i = 0; i < this.requests.length; i++) {
            if (this.requests[i].id == request.id) {
              this.requests.splice(i, 1)
              break;
            }
        }
      }
    }).catch((err) => {
      this.stopLoading();
      console.error(err);
      this.error("Internal. An error happen rejecting de requests.")
    })
  }

  accept(request) {
    this.startLoading();
    this.adminService.acceptRequest(request, this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error happen accepting de requests.")
      } else {
        this.success("Request accepted.")
      }
    }).catch((err) => {
      this.stopLoading();
      console.error(err);
      this.error("Internal. An error happen accepting de requests.")
    })
  }
}
