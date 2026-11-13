import {inject, Service, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Project} from './project.model';
import {environment} from '../environments/environment.development';

@Service()
export class ProjectService {
  httpClient = inject(HttpClient);
  router = inject(Router);
  projects = signal<Project[]>([]);
  selectedProject = signal<Project | null>(null);
  errorMessage = signal<string | null>(null);


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

}
