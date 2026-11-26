import {Component, inject} from '@angular/core';
import {TaskService} from '../task.service';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {MatCard, MatCardModule} from '@angular/material/card';
import {MatIcon} from '@angular/material/icon';
import {CommentService} from '../comment.service';
import {DatePipe} from '@angular/common';
import {FormControl, FormGroup, FormGroupDirective, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatError, MatFormField, MatHint, MatInput, MatLabel} from '@angular/material/input';
import {AuthService} from '../task-list/auth.service';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialog} from '../confirm-dialog/confirm-dialog';

@Component({
  imports: [
    RouterLink,
    MatButton,
    MatCardModule,
    MatIcon,
    DatePipe,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatError,
    MatHint
  ],
  selector: 'app-task-details',
  styleUrl: './task-details.css',
  templateUrl: './task-details.html',
})
export class TaskDetails {
  taskService = inject(TaskService);
  route = inject(ActivatedRoute);
  commentService = inject(CommentService);
  authService = inject(AuthService);
  dialog = inject(MatDialog);
  taskId = Number(this.route.snapshot.paramMap.get('id'));

  // Same limit as @Size(max = 255) in the backend's CreateCommentRequest
  readonly maxCommentLength = 255;

  commentForm = new FormGroup({
    content: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.maxLength(this.maxCommentLength)],
    }),
  });

  constructor() {
    this.taskService.errorMessage.set(null);
    this.taskService.loadTask(this.taskId);
    this.commentService.loadComments(this.taskId);
  }

  onSubmit(formDirective: FormGroupDirective) {
    if (this.commentForm.invalid) {
      return;
    }
    this.commentService.addComment(this.taskId, this.commentForm.controls.content.value);
    // resetForm() calls commentForm.reset() and also clears the "submitted" state,
    // so the empty textarea is not shown as an error right after sending
    formDirective.resetForm();
  }
  onDelete() {
    const title = this.taskService.selectedTask()?.title;
    this.dialog.open(ConfirmDialog, {
      data: {title: "Delete task", message: `Do you want to delete the task "${title}"?`}
    }).afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.taskService.deleteTask(this.taskId);
      }
    });
  }

  protected readonly TaskService = TaskService;
}


