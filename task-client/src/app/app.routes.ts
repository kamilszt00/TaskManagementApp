import { Routes } from '@angular/router';
import { Login } from  './login/login';
import { TaskList} from './task-list/task-list';
import {authGuard} from './auth-guard';
import {TaskDetails} from './task-details/task-details';
import {TaskCreate} from './task-create/task-create';
import {ProjectList} from './project-list/project-list';
import {ProjectDetails} from './project-details/project-details';
import {roleGuard} from './role-guard';


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
    canActivate: [authGuard,roleGuard(['ADMIN'])]
  },
  {
    path: '',
    redirectTo: 'projects',
    pathMatch: 'full'
  },
  {
    path:'tasks/new',
    title:'Create new task',
    component: TaskCreate,
    canActivate: [authGuard, roleGuard(['ADMIN','MANAGER'])]
  },
  {
    path:'tasks/:id',
    title:'Details about the task',
    component: TaskDetails,
    canActivate: [authGuard]
  },
  {
    path:'projects',
    title:'Your projects',
    component: ProjectList,
    canActivate: [authGuard]
  },
  {
    path: 'projects/:id',
    title: 'Project details',
    component: ProjectDetails,
    canActivate: [authGuard]
  }

];
