import {Component, inject} from '@angular/core';
import {ProjectService} from '../project.service';
import {MatCard, MatCardContent} from '@angular/material/card';
import {RouterLink} from '@angular/router';
import {MatIcon} from '@angular/material/icon';


@Component({
  imports: [
    MatCard,
    MatCardContent,
    RouterLink,
    MatIcon,
  ],
  selector: 'app-project-list',
  styleUrl: './project-list.css',
  templateUrl: './project-list.html',
})
export class ProjectList {
  projectService = inject(ProjectService);

  constructor() {
    this.projectService.loadMyProjects();
  }
}
