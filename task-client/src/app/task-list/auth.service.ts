import {inject, Service, signal} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Router} from '@angular/router';
import {TaskService} from '../task.service';
// plus the import for your service decorator, same as in task.service.ts

// the same decorator you used on TaskService
@Service()
export class AuthService {
  // 1. get HttpClient, the same way TaskList gets TaskService: inject(...)
  httpClient = inject(HttpClient);
  router = inject(Router);
  taskService = inject(TaskService);
  errorMessage = signal<string | null>(null);


  token = signal<string | null>(null);


  login(credentials : {username: string ;password : string}) {
    this.httpClient.post("http://localhost:8080/authenticate",credentials,{ responseType: 'text' }).subscribe({
      next: token => {
        this.token.set(token);
        this.router.navigate(['/tasks'])
      },
      error: err => this.errorMessage.set(err.error.message + " | " + err.status),
    });




  }


}

