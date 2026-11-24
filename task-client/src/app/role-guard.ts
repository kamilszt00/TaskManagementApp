import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from './task-list/auth.service';
import {inject} from '@angular/core';

export const adminGuard: CanActivateFn = (route, state) => {

  const authService = inject(AuthService);
  if (authService.isAdmin()) {
    return true;
  } else {
    return inject(Router).createUrlTree(['/projects']);
  }
};
