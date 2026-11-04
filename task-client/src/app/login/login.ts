import {Component, inject} from '@angular/core';
import {FormGroup, FormControl, ReactiveFormsModule,Validators} from '@angular/forms';
import {AuthService} from '../task-list/auth.service';


@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {
  authService = inject(AuthService);
  loginForm = new FormGroup({
    username: new FormControl('', {nonNullable: true, validators: Validators.required}),
    password: new FormControl('', {nonNullable: true, validators: Validators.required}),
  })


  onSubmit() {
      this.authService.login(this.loginForm.getRawValue())
  }
}
