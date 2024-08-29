import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from '../../environments/environment';

interface AuthResponse {
  jwt: string;
  aliasType: string;   // Add these fields to the interface
  aliasValue: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private aliasType: string | null = null;
  private aliasValue: string | null = null;
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient, private router: Router) {}

  getAliasType(): string | null {
    return localStorage.getItem('aliasType');
  }

  getAliasValue(): string | null {
    return localStorage.getItem('aliasValue');
  }

  login(username: string, password: string): Observable<any> {
    const body = { username, password };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<AuthResponse>(`${environment.apiUrl}/auth/login`, body, { headers }).pipe(
      tap(response => {
        console.log('Response from backend:', response);
        this.setSession(response.jwt);
        this.setAlias(response.aliasType, response.aliasValue);
      })
    );
  }

  private setSession(token: string): void {
    console.log('Token:', token);
    localStorage.setItem('authToken', token);
  }

  logout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('aliasType');
    localStorage.removeItem('aliasValue');
    this.router.navigate(['/login']);
  }

  private setAlias(aliasType: string, aliasValue: string): void {
    localStorage.setItem('aliasType', aliasType);
    localStorage.setItem('aliasValue', aliasValue);
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
