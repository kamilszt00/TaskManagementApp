import {Component, inject} from '@angular/core';
import {FormGroup, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {MatInput} from '@angular/material/input';
import {MatFormField, MatError, MatLabel} from '@angular/material/input';
import {MatSelect, MatOption} from '@angular/material/select';
import {MatIcon} from '@angular/material/icon';
import {UserService} from '../userService';
import {UserRole} from '../user.model';


@Component({
  imports: [ReactiveFormsModule, RouterLink, MatButton, MatInput, MatFormField, MatError, MatLabel, MatSelect, MatOption, MatIcon],
  selector: 'app-user-create',
  styleUrl: './user-create.css',
  templateUrl: './user-create.html',
})
export class UserCreate {
  userService = inject(UserService);

  userCreateForm = new FormGroup({
    username: new FormControl('', {nonNullable: true, validators: Validators.required}),
    email: new FormControl('', {nonNullable: true, validators: [Validators.required, Validators.email]}),
    password: new FormControl('', {nonNullable: true, validators: Validators.required}),
    role: new FormControl<UserRole>('EMPLOYEE', {nonNullable: true, validators: Validators.required}),
  })

  constructor() {
    this.userService.errorMessage.set(null);
  }

  onSubmit() {
    // getRawValue() is already { username, email, password, role }, the shape of CreateUserRequest
    this.userService.createUser(this.userCreateForm.getRawValue());
  }

}
