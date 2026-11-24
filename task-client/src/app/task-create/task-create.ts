import {Component, inject} from '@angular/core';
import {FormGroup, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {TaskService} from '../task.service';
import {CreateTaskRequest, TaskStatus} from '../task.model';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {MatInput} from '@angular/material/input';
import {MatFormField, MatError, MatLabel} from '@angular/material/input';
import {MatSelect, MatOption} from '@angular/material/select';
import {MatIcon} from '@angular/material/icon';
import {ProjectService} from '../project.service';


@Component({
  imports: [ReactiveFormsModule, RouterLink,MatButton, MatInput, MatFormField,MatError, MatLabel, MatSelect, MatOption, MatIcon],
  selector: 'app-task-create',
  styleUrl: './task-create.css',
  templateUrl: './task-create.html',
})
export class TaskCreate {
  taskService = inject(TaskService);
  route = inject(ActivatedRoute)
  projectService = inject(ProjectService);
  protected readonly projectId = Number(this.route.snapshot.queryParamMap.get('projectId'))
  taskCreateForm = new FormGroup({
    title: new FormControl('',{nonNullable: true, validators: Validators.required}),
    status: new FormControl<TaskStatus>('TODO',{nonNullable: true, validators: Validators.required}),
    dueDate: new FormControl('',{nonNullable: true, validators: Validators.required}),
    projectID: new FormControl(this.projectId, { nonNullable: true, validators: [Validators.required, Validators.min(1)] }),
    assigneeID: new FormControl(0,{nonNullable: true, validators : [Validators.required,Validators.min(1)]}),
    tagsID: new FormControl('',{nonNullable: true, validators: Validators.required})

  })
  onSubmit() {
    const createTaskForm = this.taskCreateForm.getRawValue();
    const request: CreateTaskRequest = {
      ...createTaskForm,
      tagsID: createTaskForm.tagsID.split(',').map(tag => Number(tag)),
    };
    this.taskService.createTaskRequest(request);
  }
  constructor() {
    this.projectService.loadMembers(this.projectId);
  }
}


