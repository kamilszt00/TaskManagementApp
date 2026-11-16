import {Component, inject, signal} from '@angular/core';
import {AuthService} from './task-list/auth.service';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MatToolbar, MatToolbarRow} from '@angular/material/toolbar';
import {MatButton, MatIconButton} from '@angular/material/button';
import {TaskService} from './task.service';
import {MatIcon} from '@angular/material/icon';
import {ProjectService} from './project.service';
import {CommentService} from './comment.service';

@Component({
  imports: [RouterOutlet, MatToolbarRow, MatButton, MatToolbar, RouterLink, MatIconButton, MatIcon],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('task-client');
  protected readonly authService = inject(AuthService);
  protected readonly taskService = inject(TaskService);
  protected readonly projectService = inject(ProjectService);
  protected readonly commentService = inject(CommentService);


  logout() {
    this.authService.logout();
    this.taskService.clear();
    this.projectService.clear();
    this.commentService.clear();
  }
}
