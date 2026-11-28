import {Component, effect, inject} from '@angular/core';
import {FormGroup, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {MatInput} from '@angular/material/input';
import {MatFormField, MatError, MatLabel} from '@angular/material/input';
import {MatIcon} from '@angular/material/icon';
import {ProjectService} from '../project.service';


@Component({
  imports: [ReactiveFormsModule, RouterLink, MatButton, MatInput, MatFormField, MatError, MatLabel, MatIcon],
  selector: 'app-project-edit',
  styleUrl: './project-edit.css',
  templateUrl: './project-edit.html',
})
export class ProjectEdit {
  projectService = inject(ProjectService);
  route = inject(ActivatedRoute);
  // The id is part of the path (/projects/3/edit), so paramMap
  protected readonly projectId = Number(this.route.snapshot.paramMap.get('id'));

  // Same limits as @Size(max = ...) in the backend's UpdateProjectRequest
  readonly maxNameLength = 35;
  readonly maxDescriptionLength = 255;

  projectEditForm = new FormGroup({
    name: new FormControl('', {nonNullable: true, validators: [Validators.required, Validators.maxLength(this.maxNameLength)]}),
    description: new FormControl('', {nonNullable: true, validators: [Validators.required, Validators.maxLength(this.maxDescriptionLength)]}),
  })

  constructor() {
    this.projectService.errorMessage.set(null);
    this.projectService.loadProject(this.projectId);

    // Runs again every time selectedProject() changes, so the form is filled once the project arrives
    effect(() => {
      const project = this.projectService.selectedProject();
      // Not loaded yet, or still the old project from another page
      if (!project || project.id !== this.projectId) return;
      this.projectEditForm.patchValue({
        name: project.name,
        description: project.description,
      });
    });
  }

  onSubmit() {
    // getRawValue() is already { name, description }, the shape of UpdateProjectRequest
    this.projectService.updateProject(this.projectId, this.projectEditForm.getRawValue());
  }

}
