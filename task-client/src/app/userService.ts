import {inject, Service, signal} from '@angular/core';
import {User} from './user.model';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';


@Service()
export class UserService {
  employees = signal<User[]>([]);
  errorMessage = signal<string | null>(null);
  httpClient = inject(HttpClient);

  loadEmployees() {
    this.httpClient.get<User[]>(`${environment.apiUrl}/user?role=EMPLOYEE`).subscribe({
      next: users => {
        this.employees.set(users);
        this.errorMessage.set(null);
      },
      error: () => this.errorMessage.set(null)
    })
  }

  clear() {
    this.errorMessage.set(null);
    this.employees.set([]);
  }

}
