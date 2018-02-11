import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm : FormGroup;

  name: string;
  email: string;
  message: string;


  loading: boolean;

  constructor(private fb:FormBuilder,private toastr:ToastrService, private userService: UserService, private router: Router) {

    this.registerForm = fb.group({
      name: ['',Validators.required],
      email: ['',[Validators.required, Validators.email]],
      message: ['', Validators.required]
    })

  }

  sendRequest(){
    this.loading = true;
    this.userService.sendRequest(this.name, this.email, this.message ).then((response) => {
      this.loading = false;
      let json = response.json();

      if(json.status == "error"){
        this.toastr.error('', "An error happen making your request. Try again later.");
      }else{
        this.toastr.success('', "Your request has been sended. An administrator will be review and send your conformation.");
        this.router.navigateByUrl('/');
      }
    }).catch((err) =>{
      console.error(err);
      this.toastr.error('', "Internal. An error happen making your request. Try again later.");
      this.loading = false;
    });
  }

  ngOnInit() {
  }

  getFieldError(form, field) {
    let _field = form.get(field);

    if (_field.invalid && (_field.dirty || _field.touched)) {

      let required = _field.errors.required;
      let minLength = _field.errors.minlength;
      let maxLength = _field.errors.maxlength;
      let email = _field.errors.email;

      if (required) {
        return "Field required"
      }

      if (minLength) {
        return `Debe ser mayor de ${minLength.requiredLength - 1} caracteres`
      }

      if (maxLength) {
        return `Must be less than ${maxLength.requiredLength + 1} characters`
      }

      if (email) {
        return `Must be a valid email`
      }

    }

    return "";
  }

}
