import {Service, signal, computed, inject} from '@angular/core'
import {CreateTaskRequest, Task, UpdateTaskRequest} from './task.model';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {environment} from '../environments/environment';

@Service()
export class TaskService {
  httpClient = inject(HttpClient);
  router = inject(Router);
  errorMessage = signal<string | null>(null);
  selectedTask = signal<Task | null>(null);
  tasks = signal<Task[]>([]);

  start(id : number) {
    this.httpClient.patch<Task>(`${environment.apiUrl}/task/${id}/start`,null).subscribe({
      next: updatedTask => this.tasks.update(tasks => tasks.map(task => task.id === updatedTask.id ? updatedTask :task)),
      error: err => console.log('Start failed', err.status),
    })
  }

  complete (id : number) {
    this.httpClient.patch<Task>(`${environment.apiUrl}/task/${id}/complete`,null).subscribe({
      next: updatedTask => {
        this.tasks.update(tasks => tasks.map(task => task.id === updatedTask.id ? updatedTask :task));},
      error: err => this.errorMessage.set(err.error.message),
    })
  }

  tasksLeft = computed(() => this.tasks().filter(task => task.status !== "COMPLETED").length);

  loadTasks() {
    this.httpClient.get<Task[]>(`${environment.apiUrl}/task/all`).subscribe({
      next: tasks => {
        this.tasks.set(tasks);
        this.errorMessage.set(null);
      },
      error: () => this.errorMessage.set("Could not load Tasks"),    });
  }

  loadTask(id : number) {
    this.httpClient.get<Task>(`${environment.apiUrl}/task/${id}`).subscribe({
      next: task => this.selectedTask.set(task),
      error: err => this.errorMessage.set(err.error?.message ?? "Could not load the task"),
    })
  }

  createTaskRequest(request : CreateTaskRequest) {
    this.httpClient.post(`${environment.apiUrl}/task`,request).subscribe({
      next:() => {
        this.router.navigate(['/projects', request.projectID]);
        this.errorMessage.set(null);
      },
      error: () => this.errorMessage.set("Cannot create task")

    })
  }
  updateTask(id: number, request: UpdateTaskRequest) {
    this.httpClient.put(`${environment.apiUrl}/task/${id}`, request).subscribe({
      next: () => {
        this.router.navigate(['/tasks', id]);
        this.errorMessage.set(null);
      },
      error: () => this.errorMessage.set("Cannot update the task")
    })
  }
  deleteTask(id:number) {
    const projectId = this.selectedTask()?.projectID;
    if (!projectId) return;
    this.httpClient.delete(`${environment.apiUrl}/task/${id}`).subscribe({
      next:() => {
        this.router.navigate(['/projects', projectId]);
        this.errorMessage.set(null);
        this.selectedTask.set(null);
      },
      error: () => this.errorMessage.set("Cannot delete the task")
    })
  }
  reassignTask(id:number, userId: number) {
    this.httpClient.patch<Task>(`${environment.apiUrl}/task/${id}/reassign`,null, {params : { userId}}).subscribe({
      next: updatedTask => {
        this.selectedTask.set(updatedTask);
        this.errorMessage.set(null);
      },
      error: () => this.errorMessage.set("Cannot reassign task"),
    })
  }
  loadProjectTasks(projectId : number) {
    this.httpClient.get<Task[]>(`${environment.apiUrl}/project/${projectId}/tasks`).subscribe({
      next: tasks => {
        this.tasks.set(tasks);
        this.errorMessage.set(null);
      },
      error: () => this.errorMessage.set("Could not load the tasks")
    })
  }

  clear() {
    this.tasks.set([]);
    this.selectedTask.set(null);
    this.errorMessage.set(null);
  }


}


