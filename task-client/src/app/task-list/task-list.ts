import {Component, inject} from '@angular/core';
import {TaskService} from '../task.service';
import {RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {MatMiniFabButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';

@Component({
  imports: [RouterLink,MatButton,MatMiniFabButton,MatIcon,MatCardModule],
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
