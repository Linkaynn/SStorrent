import { Component, OnInit } from '@angular/core';
import { BaseComponent } from '../base/base.component';
import { UserService } from '../../services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-preferences',
  templateUrl: './preferences.component.html',
  styleUrls: ['./preferences.component.scss']
})
export class PreferencesComponent extends BaseComponent {

  name : string = this.currentUser().name;
  newPassword : string = null;

  allMirrors : string[] = null;
  userMirrors : string[] = [];

  preferenceForm : FormGroup;

  constructor(private userService : UserService, private fb: FormBuilder) {
    super();

    this.retrieveMirrors();
    this.retrieveAllMirrors();

    this.preferenceForm = fb.group({
      name: ['', [Validators.required, Validators.maxLength(15)]],
      newPassword: ['', [Validators.minLength(3), Validators.maxLength(15)]],
      mirrors: ['']
    })
  }

  retrieveMirrors() {
    this.startLoading();
    this.userService.getMirrors(this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error was happend retrieving your mirrors. Probably your session expire.")
        this.logout();
      } else {
        this.currentUser().setMirrors(json.data.mirrors);
        this.currentUser().mirrors.forEach((mirror) => this.userMirrors[mirror] = true);
      }
    }).catch((err) => {
      this.stopLoading();
      console.error(err);
      this.error("Internal. An error was happend retrieving your mirrors. Reload page please.")
    })
  }

  retrieveAllMirrors() {
    this.startLoading();
    this.userService.getAllMirrors(this.currentUser().token.token).then((response) => {
      this.stopLoading();

      let json = response.json();

      if (json.status == "error") {
        this.error("An error was happend retrieving the mirrors. Probably your session expire.")
        this.logout();
      } else {
        this.allMirrors = json.data.mirrors;
      }
    }).catch((err) => {
      this.stopLoading();
      console.error(err);
      this.error("Internal. An error was happend retrieving the mirrors. Reload page please.")
    })
  }

  validProfile() {
    return this.preferenceForm.valid && this.getMirrorsFromModel().length > 0;
  }

  updateProfile() {
    this.startLoading();
    this.userService.updateProfile(this.name, this.newPassword, this.getMirrorsFromModel(), this.currentUser().token.token).then((response) =>  {
      let json = response.json();
      if (json.status == "error") {
        this.error("Error updating your profile. Try again later");
        this.stopLoading();
      } else {
        this.success("Profile updated.")
        this.currentUser().name = this.name;
        this.newPassword = "";
        this.retrieveMirrors();
      }
    }).catch((err) => {
      console.error(err);
      this.error("Internal. Error updating your profile. Try again later");
      this.stopLoading();
    })
  }

  getMirrorsFromModel() {
    let mirrors = [];
    Object.keys(this.userMirrors).forEach((mirror) => {
       if (mirror && this.userMirrors[mirror]) mirrors.push(mirror);
    });
    return mirrors;
  }

  getFieldError(form, field) {
    if (field == "mirrors" && this.getMirrorsFromModel().length == 0) {
      return "At least you must select one mirror";
    }

    return super.getFieldError(form, field);
  }

}
