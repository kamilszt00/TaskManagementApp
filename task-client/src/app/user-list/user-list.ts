import {Component, inject} from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {MatIcon} from '@angular/material/icon';
import {UserService} from '../userService';
import {MatButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';

@Component({
  imports: [MatCardModule, MatIcon, MatButton, RouterLink],
  selector: 'app-user-list',
  styleUrl: './user-list.css',
  templateUrl: './user-list.html',
})
export class UserList {
  userService = inject(UserService);

  // One card per role. `users` is the signal itself (no parentheses), the template reads it with users()
  readonly sections = [
    {title: 'Employees', icon: 'person', users: this.userService.employees, empty: 'No employees'},
    {title: 'Managers', icon: 'supervisor_account', users: this.userService.managers, empty: 'No managers'},
    {title: 'Admins', icon: 'admin_panel_settings', users: this.userService.admins, empty: 'No admins'},
  ];

  constructor() {
    // Reset once here: the three requests below run at the same time
    this.userService.errorMessage.set(null);
    this.userService.loadEmployees();
    this.userService.loadManagers();
    this.userService.loadAdmins();
  }
}
