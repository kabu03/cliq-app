import {
  provideHttpClient,
  HttpHandlerFn,
  HttpInterceptorFn,
  HttpRequest,
  HTTP_INTERCEPTORS,
  withInterceptors, withFetch
} from '@angular/common/http';
import {ApplicationConfig, importProvidersFrom, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
import {provideClientHydration} from '@angular/platform-browser';
// Example of an authentication interceptor
import {routes} from './app.routes';
import {authInterceptorFn} from './auth/auth-interceptor.service';

export const authenticationInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const modifiedReq = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${localStorage.getItem('token')}`),
  });

  return next(modifiedReq);
};

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withFetch(), withInterceptors([authInterceptorFn])),
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes),
  ],
};
