import {Component, inject, input} from '@angular/core';
import {Task} from '../task.model';
import {MatButton} from '@angular/material/button';
import {MatCard, MatCardContent} from '@angular/material/card';
import {RouterLink} from '@angular/router';
import {TaskService} from '../task.service';

@Component({
  imports: [
    MatButton,
    MatCard,
    MatCardContent,
    RouterLink
  ],
  selector: 'app-task-card',
  styleUrl: './task-card.css',
  templateUrl: './task-card.html',
})
export class TaskCard {
  task = input.required<Task>();
  taskService = inject(TaskService);
}
