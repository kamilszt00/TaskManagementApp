import {Component, inject} from '@angular/core';
import {TaskService} from '../task.service';
import {MatCardModule} from '@angular/material/card';
import {TaskCard} from '../task-card/task-card';

@Component({
  imports: [ MatCardModule, TaskCard],
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
