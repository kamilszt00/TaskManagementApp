import {inject, Service, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Project, UpdateProjectRequest} from './project.model';
import {environment} from '../environments/environment';
import {User} from './user.model';

@Service()
export class ProjectService {
  httpClient = inject(HttpClient);
  router = inject(Router);
  projects = signal<Project[]>([]);
  selectedProject = signal<Project | null>(null);
  errorMessage = signal<string | null>(null);
  members = signal<User[]>([])


  loadMyProjects() {
    this.httpClient.get<Project[]>(`${environment.apiUrl}/project/mine`).subscribe({
      next: projects => {
        this.projects.set(projects);
        this.errorMessage.set(null);
      },
      error: err => this.errorMessage.set("Could not load the projects"),
    })
  }

  loadProject(id : number) {
    this.httpClient.get<Project>(`${environment.apiUrl}/project/${id}`).subscribe({
      next: project => {
        this.selectedProject.set(project);
        this.errorMessage.set(null);
      },
      error: err => this.errorMessage.set("Could not load the project")
    })
  }

  updateProject(id: number, request: UpdateProjectRequest) {
    this.httpClient.patch(`${environment.apiUrl}/project/${id}`, request).subscribe({
      next: () => {
        this.router.navigate(['/projects', id]);
        this.errorMessage.set(null);
      },
      error: () => this.errorMessage.set("Cannot update the project")
    })
  }

  loadMembers(id:number) {
    this.httpClient.get<User[]>(`${environment.apiUrl}/project/${id}/assignees`).subscribe({
      next: users => {
          this.members.set(users);
          this.errorMessage.set(null);
        },
      error: () => this.errorMessage.set("Members could not be loaded")
    })
    }

    addMembers(projectId:number,usersID: number[]) {
      this.httpClient.post(`${environment.apiUrl}/project/${projectId}/assignees`,{ usersID }).subscribe({
        next: () => this.loadMembers(projectId),
        error: () => this.errorMessage.set("Could not add members")
      })
    }

    removeMembers(projectId:number, userId:number) {
    this.httpClient.delete(`${environment.apiUrl}/project/${projectId}/assignees/${userId}`).subscribe({
      next: () => this.members.update(users => users.filter(user => user.id !== userId)),
      error: () => this.errorMessage.set("Could not remove member"),
    })
    }

  clear() {
    this.projects.set([]);
    this.selectedProject.set(null);
    this.errorMessage.set(null);
    this.members.set([]);
  }

}
