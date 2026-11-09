import {Component, inject} from '@angular/core';
import {FormGroup, FormControl, ReactiveFormsModule,Validators} from '@angular/forms';
import {AuthService} from '../task-list/auth.service';
import {MatButton} from '@angular/material/button';
import {MatInput} from '@angular/material/input';
import {MatFormField, MatError, MatLabel} from '@angular/material/input';

@Component({
  imports: [ReactiveFormsModule, MatButton, MatInput, MatFormField,MatError, MatLabel],
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
