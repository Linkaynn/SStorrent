import { Component } from '@angular/core';
import { BaseComponent } from './base/base.component';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent extends BaseComponent {
  title = 'app';

  constructor(protected toastr: ToastrService, protected router : Router) {
    super(toastr, router);
  }
}
