import {inject, Service, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {TaskComment} from './comment.model';

@Service()
export class CommentService {
  httpClient = inject(HttpClient);
  comments = signal<TaskComment[]>([]);
  errorMessage = signal<string | null>(null);



  loadComments(taskId: number) {
    this.httpClient.get<TaskComment[]>(`${environment.apiUrl}/task/${taskId}/comments`).subscribe({
      next: comments => {
        this.comments.set(comments);
        this.errorMessage.set(null);
      },
      error: () => this.errorMessage.set("Comment cannot be loaded")
    })
  }
  addComment(taskId: number, content: string) {
    this.httpClient.post<TaskComment>(`${environment.apiUrl}/task/${taskId}/comments`, {content}).subscribe({
      next: comment => {
        this.comments.update( comments => [...comments,comment])
        this.errorMessage.set(null);
      },
      error: () => this.errorMessage.set("Cannot add new comment")
    })
  }
  clear() {
    this.comments.set([]);
    this.errorMessage.set(null);
  }
}
