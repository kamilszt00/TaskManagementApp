import { Routes } from '@angular/router';
import { Login } from  './login/login';
import { TaskList} from './task-list/task-list';
import {authGuard} from './auth-guard';
import {TaskDetails} from './task-details/task-details';
import {TaskCreate} from './task-create/task-create';


export const routes: Routes = [
  {
    path: 'login',
    title: 'Login page',
    component: Login,
  },
  {
    path: 'tasks',
    title: 'Task list page',
    component: TaskList,
    canActivate: [authGuard]
  },
  {
    path: '',
    redirectTo: 'tasks',
    pathMatch: 'full'
  },
  {
    path:'tasks/new',
    title:'Create new task',
    component: TaskCreate,
    canActivate: [authGuard]
  },
  {
    path:'tasks/:id',
    title:'Details about the task',
    component: TaskDetails,
    canActivate: [authGuard]
  },

];
