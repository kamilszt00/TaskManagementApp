import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProjectEdit } from './project-edit';

describe('ProjectEdit', () => {
  let component: ProjectEdit;
  let fixture: ComponentFixture<ProjectEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectEdit],
      // The router (routerLink / ActivatedRoute) and a fake HttpClient used by ProjectService
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
