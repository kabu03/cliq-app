import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from '../../environments/environment';

interface AuthResponse {
  jwt: string;
  // add other properties if needed
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string): Observable<any> {
    const body = { username, password };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<AuthResponse>(`${environment.apiUrl}/auth/login`, body, { headers }).pipe(
      tap(response => {
        console.log('Response from backend:', response);
        this.setSession(response.jwt);
      })
    );
  }

  private setSession(token: string): void {
    console.log('Token:', token);
    localStorage.setItem('authToken', token);
  }

  logout(): void {
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    if (typeof window !== 'undefined' && window.localStorage) {
      // Safely access localStorage here
      const token = localStorage.getItem('authToken');
      return token != null && !this.jwtHelper.isTokenExpired(token);
    }
    return false;
  }


  getToken(): string | null {
    return localStorage.getItem('authToken');
  }
}



// import { Injectable } from '@angular/core';
//
// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//
//   constructor() { }
// }
