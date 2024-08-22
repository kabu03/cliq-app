import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from "@angular/forms";
import {CommonModule, NgFor, NgIf} from "@angular/common";
import { environment } from '../../environments/environment';



@Component({
  selector: 'app-transaction-search',
  templateUrl: './transaction-search.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgFor,
    CommonModule,
    HttpClientModule
  ],
  styleUrls: ['./transaction-search.component.css']
})
export class TransactionSearchComponent {
  searchType: string = 'all';
  aliasSearchType: string = 'all';
  aliasType: string = 'IBAN';
  aliasValue: string = '';
  transactions: any[] = []; // Store fetched transactions
  searchAttempted: boolean = false; // Flag to check if search has been attempted

  constructor(private router: Router, private http: HttpClient) {}

  onSearchTypeChange() {
    // Reset alias-specific fields when switching search type
    if (this.searchType !== 'alias') {
      this.aliasSearchType = 'all';
      this.aliasType = 'IBAN';
      this.aliasValue = '';
    }
  }

  onSubmit() {
    this.searchAttempted = true; // Mark that search has been attempted
    let url:string = `${environment.apiUrl}`;

    if (this.searchType === 'all') {
      url += '/transactions/all';
    } else if (this.searchType === 'alias') {
      url += `/transactions/${this.aliasSearchType}/${this.aliasType}/${this.aliasValue}`;
    }

    // Send the GET request using HttpClient
    this.http.get(url).subscribe(
      (response: any) => {
        this.transactions = response.transactionList; // Assuming transactionList is returned in this case
        console.log('Transactions:', this.transactions); // Logging the result
      },
      (error) => {
        console.error('Error fetching transactions:', error);
      }
    );
  }
}
