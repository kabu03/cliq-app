import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router, RouterLink, RouterOutlet} from "@angular/router";
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import {MatLine} from "@angular/material/core";
import {MatIconButton} from "@angular/material/button";
import {Title} from "@angular/platform-browser";
import {filter, map, mergeMap} from "rxjs";
import {AuthService} from "./auth/auth.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [
    RouterLink,
    RouterOutlet,
    MatIconModule,
    MatToolbarModule,
    MatListModule,
    MatSidenavModule,
    MatLine,
    MatIconButton,
  ],
})
export class AppComponent implements OnInit {
  loggedInUserAlias: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    // Subscribe to router events
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.updateLoggedInUserAlias();
    });

    // Initialize on first load
    this.updateLoggedInUserAlias();
  }

  updateLoggedInUserAlias() {
    const aliasType = this.authService.getAliasType();
    const aliasValue = this.authService.getAliasValue();
    if (aliasType && aliasValue) {
      this.loggedInUserAlias = `${aliasValue}`;
    } else {
      this.loggedInUserAlias = 'Guest';
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
    this.updateLoggedInUserAlias(); // Update alias after logging out
  }
}
