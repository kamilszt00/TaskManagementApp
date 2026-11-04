import {Component, inject, signal} from '@angular/core';
import {AuthService} from './task-list/auth.service';
import { RouterOutlet} from '@angular/router';

@Component({
  imports: [ RouterOutlet],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('task-client');
  protected readonly authService = inject(AuthService);


}
