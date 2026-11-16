import {Component, computed, inject} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {ProjectService} from '../project.service';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from '@angular/material/card';
import {TaskService} from '../task.service';
import {TaskCard} from '../task-card/task-card';
import {MatIcon} from '@angular/material/icon';
import {MatButton, MatIconButton} from '@angular/material/button';
import {AuthService} from '../task-list/auth.service';
import {UserService} from '../userService';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';

@Component({
  imports: [
    RouterLink,
    MatCard,
    MatCardContent,
    MatCardHeader,
    TaskCard,
    MatIcon,
    MatButton,
    MatFormField,
    MatSelect,
    ReactiveFormsModule,
    MatCardTitle,
    MatLabel,
    MatOption,
    MatIconButton
  ],
  selector: 'app-project-details',
  styleUrl: './project-details.css',
  templateUrl: './project-details.html',
})
export class ProjectDetails {
  projectService = inject(ProjectService)
  route = inject(ActivatedRoute)
  taskService = inject(TaskService)
  authService = inject(AuthService);
  userService = inject(UserService);

  canManage = computed(() => this.authService.role() === 'MANAGER' || this.authService.role() === 'ADMIN');
  availableEmployees = computed(() =>
    this.userService.employees().filter(e => !this.projectService.members().some(m => m.id === e.id))

  );
  protected readonly projectId = Number(this.route.snapshot.paramMap.get('id'));
  selectedEmployee = new FormControl<number | null>(null);

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.projectService.loadProject(id);
    this.taskService.loadProjectTasks(id);
    if (this.canManage()) {
      this.userService.loadEmployees();
      this.projectService.loadMembers(id);
    }
  }


  onAddMember() {
    const userId = this.selectedEmployee.value;
    if (userId !== null) {
      this.projectService.addMembers(this.projectId, [userId]);
      this.selectedEmployee.reset();
    }
  }

}
