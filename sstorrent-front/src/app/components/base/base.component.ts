import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";
import { OnInit } from "@angular/core/core";

// Models
import { User } from "../../models/user";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";

export class BaseComponent implements OnInit {
  protected static toastr : ToastrService;
  protected static router : Router;
  protected static modalService : NgbModal;
  protected static currentUser : User = null;
  public static loading : boolean = false;


  constructor(protected toastr?: ToastrService, protected router?: Router, protected modalService? : NgbModal) {
    if (BaseComponent.toastr == null) {
      BaseComponent.toastr = toastr;
    }

    if (BaseComponent.router == null) {
      BaseComponent.router = router;
    }

    if (BaseComponent.modalService == null) {
      BaseComponent.modalService = modalService;
    }

    if (BaseComponent.currentUser == null && localStorage.getItem("user")) {
      this.setCurrentUser();
    }
  }

  public login(user) {
    localStorage.setItem("user", JSON.stringify(user));
    this.setCurrentUser();
  }

  public logout() {
    localStorage.clear();
    BaseComponent.currentUser = null;
    this.navigateTo('/');
  }

  private setCurrentUser() {
    let user = JSON.parse(localStorage.getItem("user"));
    BaseComponent.currentUser = new User(user.name, user.token);
  }

  public error(message) {
    BaseComponent.toastr.error('', message);
  }

  public success(message) {
    BaseComponent.toastr.success('', message);
  }

  public navigateTo(url) {
    BaseComponent.router.navigateByUrl(url);
  }

  public isLogged() {
    return BaseComponent.currentUser != null;
  }

  public currentUser() {
    return BaseComponent.currentUser;
  }

  public openModal(component) {
    return BaseComponent.modalService.open(component);
  }

  // Validators
  getFieldError(form, field) {
    let _field = form.get(field);

    if (_field.invalid && (_field.dirty || _field.touched)) {

      let required = _field.errors.required;
      let minLength = _field.errors.minlength;
      let maxLength = _field.errors.maxlength;
      let pattern = _field.errors.pattern;

      if (required) {
        return "Field required"
      }

      if (minLength) {
        return `Debe ser mayor de ${minLength.requiredLength - 1} caracteres`
      }

      if (maxLength) {
        return `Must be less than ${maxLength.requiredLength + 1} characters`
      }

      if (pattern) {
        return `Debe contener sólo números`
      }

    }

    return "";
  }

  public startLoading() {
    BaseComponent.loading = true;
  }

  public stopLoading() {
    BaseComponent.loading = false;
  }

  public isLoading() {
    return BaseComponent.loading;
  }

  ngOnInit() {

  }
}
