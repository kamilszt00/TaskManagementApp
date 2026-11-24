import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from './task-list/auth.service';
import {inject} from '@angular/core';
import {Role} from './task-list/auth.model';

export function roleGuard(allowedRoles: Role[]): CanActivateFn {

  const guard: CanActivateFn = (route, state) => {
    const authService = inject(AuthService);
    const role = authService.role();
    if (role !== null && allowedRoles.includes(role)) {
      return true;
    } else {
      return inject(Router).createUrlTree(['/projects']);
    }
  };

  return guard;
}
