import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TaskCreate } from './task-create';

describe('TaskCreate', () => {
  let component: TaskCreate;
  let fixture: ComponentFixture<TaskCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskCreate],
      // The router (routerLink / ActivatedRoute) and a fake HttpClient used by TaskService
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(TaskCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
