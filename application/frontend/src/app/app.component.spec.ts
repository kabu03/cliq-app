import { TestBed, ComponentFixture } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { RouterTestingModule } from '@angular/router/testing';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  let app: AppComponent;
  let titleService: Title;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [AppComponent],
      providers: [
        Title,
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({ title: 'Test Title' }),
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    app = fixture.componentInstance;
    titleService = TestBed.inject(Title);
  });

  it('should create the app', () => {
    expect(app).toBeTruthy();
  });

  it('should set the title from route data', () => {
    fixture.detectChanges();
    expect(titleService.getTitle()).toBe('Test Title');
  });
});
