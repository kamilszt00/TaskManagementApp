import {inject, Service, signal} from '@angular/core';
import {CreateUserRequest, User} from './user.model';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {Router} from '@angular/router';


@Service()
export class UserService {
  employees = signal<User[]>([]);
  managers = signal<User[]>([]);
  admins = signal<User[]>([]);
  errorMessage = signal<string | null>(null);
  httpClient = inject(HttpClient);
  router = inject(Router);

  loadEmployees() {
    this.httpClient.get<User[]>(`${environment.apiUrl}/user?role=EMPLOYEE`).subscribe({
      next: users => {
        this.employees.set(users);
      },
      error: () => this.errorMessage.set("Cannot load Employees")
    })
  }
  loadManagers() {
    this.httpClient.get<User[]>(`${environment.apiUrl}/user?role=MANAGER`).subscribe({
      next: users => {
        this.managers.set(users);
      },
      error: () => this.errorMessage.set("Cannot load Managers")
    })
  }
  loadAdmins() {
    this.httpClient.get<User[]>(`${environment.apiUrl}/user?role=ADMIN`).subscribe({
      next: users => {
        this.admins.set(users);
      },
      error: () => this.errorMessage.set("Cannot load admins")
    })
  }
  createUser(request: CreateUserRequest) {
    this.httpClient.post(`${environment.apiUrl}/user`, request).subscribe({
      next: () => {
        this.router.navigate(['/users']);
        this.errorMessage.set(null);
      },
      // A duplicate username or email returns only the generic 400, so use our own message
      error: () => this.errorMessage.set("Cannot create the user. The username or email may already be in use.")
    })
  }

  clear() {
    this.errorMessage.set(null);
    this.employees.set([]);
    this.admins.set([]);
    this.managers.set([]);
  }

}
