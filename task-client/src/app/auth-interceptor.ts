import { HttpInterceptorFn } from '@angular/common/http';
import {inject} from '@angular/core';
import {AuthService} from './task-list/auth.service';
import {catchError} from 'rxjs';
import {throwError} from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.token();
  let outgoing = req;
  if (token) {
    outgoing = req.clone({setHeaders: { Authorization: `Bearer ${token}`}})
  }
  return next(outgoing).pipe(
    catchError(err => {
      if (err.status === 401) {
        authService.logout();
      }
      return throwError(() => err);
    })
  );
};
