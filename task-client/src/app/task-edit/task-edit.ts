import {Component, effect, inject} from '@angular/core';
import {FormGroup, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {TaskService} from '../task.service';
import {TaskStatus, UpdateTaskRequest} from '../task.model';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {MatInput} from '@angular/material/input';
import {MatFormField, MatError, MatLabel} from '@angular/material/input';
import {MatSelect, MatOption} from '@angular/material/select';
import {MatIcon} from '@angular/material/icon';
import {ProjectService} from '../project.service';


@Component({
  imports: [ReactiveFormsModule, RouterLink, MatButton, MatInput, MatFormField, MatError, MatLabel, MatSelect, MatOption, MatIcon],
  selector: 'app-task-edit',
  styleUrl: './task-edit.css',
  templateUrl: './task-edit.html',
})
export class TaskEdit {
  taskService = inject(TaskService);
  route = inject(ActivatedRoute);
  projectService = inject(ProjectService);
  // The id is part of the path (/tasks/5/edit), so paramMap, like in task-details
  protected readonly taskId = Number(this.route.snapshot.paramMap.get('id'));
  taskEditForm = new FormGroup({
    title: new FormControl('', {nonNullable: true, validators: Validators.required}),
    status: new FormControl<TaskStatus>('TODO', {nonNullable: true, validators: Validators.required}),
    // 0 until the task is loaded; the effect() in the constructor fills it in
    projectID: new FormControl(0, {nonNullable: true, validators: [Validators.required, Validators.min(1)]}),
    assigneeID: new FormControl(0, {nonNullable: true, validators: [Validators.required, Validators.min(1)]}),
    tagsID: new FormControl('', {nonNullable: true, validators: Validators.required})
  })

  constructor() {
    this.taskService.errorMessage.set(null);
    this.taskService.loadTask(this.taskId);

    // Runs again every time selectedTask() changes, so the form is filled once the task arrives
    effect(() => {
      const task = this.taskService.selectedTask();
      // Not loaded yet, or still the old task from another page
      if (!task || task.id !== this.taskId) return;
      this.taskEditForm.patchValue({
        title: task.title,
        status: task.status,
        projectID: task.projectID,
        assigneeID: task.assigneeID,
        tagsID: task.tagsID.join(','),
      });
      this.projectService.loadMembers(task.projectID);
    });
  }

  onSubmit() {
    const editTaskForm = this.taskEditForm.getRawValue();
    const request: UpdateTaskRequest = {
      ...editTaskForm,
      tagsID: editTaskForm.tagsID.split(',').map(tag => Number(tag)),
    };
    this.taskService.updateTask(this.taskId, request);
  }

}
