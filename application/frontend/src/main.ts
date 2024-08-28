import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {appConfig} from "./app/app.config";
import {provideHttpClient, withFetch, withInterceptors} from "@angular/common/http";
import {authInterceptorFn} from "./app/auth/auth-interceptor.service";

bootstrapApplication(AppComponent, {
  providers: [provideRouter(routes), provideAnimationsAsync(), provideHttpClient(withFetch(), withInterceptors([authInterceptorFn]))]
}).then(r => console.log('Application has been bootstrapped!'));

// bootstrapApplication(AppComponent, appConfig).then(r => console.log('Application has been bootstrapped!'));
