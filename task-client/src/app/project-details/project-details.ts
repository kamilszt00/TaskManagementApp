import {Component, inject} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {ProjectService} from '../project.service';
import {MatCard, MatCardContent, MatCardHeader} from '@angular/material/card';
import {TaskService} from '../task.service';
import {TaskCard} from '../task-card/task-card';

@Component({
  imports: [
    RouterLink,
    MatCard,
    MatCardContent,
    MatCardHeader,
    TaskCard
  ],
  selector: 'app-project-details',
  styleUrl: './project-details.css',
  templateUrl: './project-details.html',
})
export class ProjectDetails {
  projectService = inject(ProjectService)
  route = inject(ActivatedRoute)
  taskService = inject(TaskService)

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.projectService.loadProject(id);
    this.taskService.loadProjectTasks(id);
  }
}
