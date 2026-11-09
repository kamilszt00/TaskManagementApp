import {Component, inject} from '@angular/core';
import {TaskService} from '../task.service';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {MatCard, MatCardModule} from '@angular/material/card';
import {MatIcon} from '@angular/material/icon';

@Component({
  imports: [
    RouterLink,
    MatButton,
    MatCardModule,
    MatIcon
  ],
  selector: 'app-task-details',
  styleUrl: './task-details.css',
  templateUrl: './task-details.html',
})
export class TaskDetails {
  taskService = inject(TaskService);
  route = inject(ActivatedRoute);

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.taskService.loadTask(id);
  }

  protected readonly TaskService = TaskService;
}


