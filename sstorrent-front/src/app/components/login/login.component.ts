import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from 'ngx-toastr';

// Services
import { UserService } from '../../services/user.service';
import { BaseComponent } from '../base/base.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent extends BaseComponent  {
  username : string;
  password : string;

  loginForm : FormGroup;

  constructor(private fb: FormBuilder, private userService: UserService) {
    super();
    if (this.isLogged()) {
      this.navigateTo("/home");
    }

    this.loginForm = fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  onLogin() {
    this.startLoading();

    this.userService.login(this.username, this.password).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("Invalid username or password");
      } else {
        this.login(json.data);
        this.navigateTo("/home")
      }
    }).catch((err) => {
      this.stopLoading();
      console.error(err);
    })
  }

}
