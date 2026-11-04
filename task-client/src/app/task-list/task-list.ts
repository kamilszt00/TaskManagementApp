import {Component, inject} from '@angular/core';
import {TaskService} from '../task.service';
import {RouterLink} from '@angular/router';

@Component({
  imports: [
    RouterLink
  ],
  selector: 'app-task-list',
  styleUrl: './task-list.css',
  templateUrl: './task-list.html',
})
export class TaskList {
  taskService = inject(TaskService);

  constructor() {
    this.taskService.loadTasks();

  }

}
