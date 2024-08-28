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
  constructor(
    private titleService: Title,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => {
        let route = this.activatedRoute;
        while (route.firstChild) route = route.firstChild;
        return route;
      }),
      mergeMap(route => route.data)
    ).subscribe(data => {
      this.titleService.setTitle(data['title'] || 'Default Title');
    });
  }
}
