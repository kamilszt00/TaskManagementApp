import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserCreate } from './user-create';

describe('UserCreate', () => {
  let component: UserCreate;
  let fixture: ComponentFixture<UserCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserCreate],
      // The router (routerLink / ActivatedRoute) and a fake HttpClient used by UserService
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(UserCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
