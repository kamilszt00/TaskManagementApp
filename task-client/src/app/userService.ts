import {inject, Service, signal} from '@angular/core';
import {User} from './user.model';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';


@Service()
export class UserService {
  employees = signal<User[]>([]);
  managers = signal<User[]>([]);
  admins = signal<User[]>([]);
  errorMessage = signal<string | null>(null);
  httpClient = inject(HttpClient);

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

  clear() {
    this.errorMessage.set(null);
    this.employees.set([]);
    this.admins.set([]);
    this.managers.set([]);
  }

}
