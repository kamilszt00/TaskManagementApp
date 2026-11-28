import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TaskEdit } from './task-edit';

describe('TaskEdit', () => {
  let component: TaskEdit;
  let fixture: ComponentFixture<TaskEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskEdit],
      // The router (routerLink / ActivatedRoute) and a fake HttpClient used by TaskService
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(TaskEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
