import {computed, inject, Service, signal} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Router} from '@angular/router';
import {TaskService} from '../task.service';
import {environment} from '../../environments/environment';
import {Role} from './auth.model';

@Service()
export class AuthService {
  private readonly TOKEN_KEY = "jwtKey";
  httpClient = inject(HttpClient);
  router = inject(Router);
  errorMessage = signal<string | null>(null);
  token = signal<string | null>(localStorage.getItem(this.TOKEN_KEY));
  role = computed(()=> {
    const token = this.token();
    if (!token) {
      return null;
    }
    const parts = token.split('.');
    const payload = parts[1];

    const payloadBase64 = payload.replaceAll('-', '+').replaceAll('_', '/');
    const text = atob(payloadBase64);
    const data = JSON.parse(text);

    return data.role as Role;
  })

  login(credentials : {username: string ;password : string}) {
    this.httpClient.post(`${environment.apiUrl}/authenticate`,credentials,{ responseType: 'text' }).subscribe({
      next: token => {
        this.token.set(token);
        localStorage.setItem(this.TOKEN_KEY,token)
        this.errorMessage.set(null);
        this.router.navigate(['/tasks'])
      },
      error: err => this.errorMessage.set("Invalid username or password" + " | " + err.status),
    });
  }
  logout() {
    this.token.set(null);
    localStorage.removeItem(this.TOKEN_KEY)
    this.router.navigate(['/login']);
  }





}

