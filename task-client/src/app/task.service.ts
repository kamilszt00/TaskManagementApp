import {Service, signal, computed, inject} from '@angular/core'
import {CreateTaskRequest, Task} from './task.model';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Service()
export class TaskService {
  httpClient = inject(HttpClient);
  router = inject(Router);
  errorMessage = signal<string | null>(null);
  selectedTask = signal<Task | null>(null);


  tasks = signal<Task[]>([]);

  start(id : number) {
    this.httpClient.patch<Task>(`http://localhost:8080/task/${id}/start`,null).subscribe({
      next: updatedTask => this.tasks.update(tasks => tasks.map(task => task.id === updatedTask.id ? updatedTask :task)),
      error: err => console.log('Start failed', err.status),
    })
  }

  complete (id : number) {
    this.httpClient.patch<Task>(`http://localhost:8080/task/${id}/complete`,null).subscribe({
      next: updatedTask => {
        this.tasks.update(tasks => tasks.map(task => task.id === updatedTask.id ? updatedTask :task));},
      error: err => this.errorMessage.set(err.error.message),
    })
  }

  tasksLeft = computed(() => this.tasks().filter(task => task.status !== "COMPLETED").length);

  loadTasks() {
    this.httpClient.get<Task[]>("http://localhost:8080/task/all").subscribe({
      next: tasks => this.tasks.set(tasks),
      error: err => console.log('Tasks cannot be retrieved', err.status),
    });
  }

  loadTask(id : number) {
    this.httpClient.get<Task>(`http://localhost:8080/task/${id}`).subscribe({
      next: task => this.selectedTask.set(task),
      error: err => this.errorMessage.set(err.error.message),
    })
  }

  createTaskRequest(request : CreateTaskRequest) {
    this.httpClient.post("http://localhost:8080/task",request).subscribe({
      next:() => this.router.navigate(['/tasks']),

    })
  }


}


