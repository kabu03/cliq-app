import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from "@angular/forms";
import {CommonModule, NgFor, NgIf} from "@angular/common";
import { environment } from '../../environments/environment';
import { AgGridAngular } from 'ag-grid-angular'; // Angular Data Grid Component
import { ColDef } from 'ag-grid-community'; // Column Definition Type Interface
import {MatRadioModule} from '@angular/material/radio';
import {MatInputModule} from '@angular/material/input';
import {MatOption} from "@angular/material/core";
import {MatSelect} from "@angular/material/select";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-transaction-search',
  templateUrl: './transaction-search.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgFor,
    CommonModule,
    HttpClientModule,
    MatRadioModule,
    MatInputModule,
    AgGridAngular,
    MatOption,
    MatSelect,
    MatButton,
    // Importing the AgGridAngular component
  ],
  styleUrls: ['./transaction-search.component.css']
})
export class TransactionSearchComponent {
  searchType: string = 'all';
  aliasSearchType: string = 'all';
  aliasType: string = 'ALPHANUMERIC';
  aliasValue: string = '';
  transactions: any[] = []; // Store fetched transactions
  searchAttempted: boolean = false; // Flag to check if search has been attempted

  // Define the column definitions
  colDefs: ColDef[] = [
    { field: 'amount', headerName: 'Amount' },
    { field: 'currency', headerName: 'Currency' },
    { field: 'purpose', headerName: 'Purpose' },
    { field: 'timestamp', headerName: 'Timestamp' },
    { field: 'debtor.value', headerName: 'Debtor' },
    { field: 'creditor.value', headerName: 'Creditor' },
  ];
  themeClass =
    "ag-theme-quartz-dark";

  rowData: any[] = [];

  constructor(private router: Router, private http: HttpClient) {}

  onSearchTypeChange() {
    // Reset alias-specific fields when switching search type
    if (this.searchType !== 'alias') {
      this.aliasSearchType = 'all';
      this.aliasType = 'ALPHANUMERIC';
      this.aliasValue = '';
    }
  }

  onSubmit() {
    this.searchAttempted = true; // Mark that search has been attempted
    let url: string = `${environment.apiUrl}`;

    if (this.searchType === 'all') {
      url += '/transactions/all';
    } else if (this.searchType === 'alias') {
      url += `/transactions/${this.aliasSearchType}/${this.aliasType}/${this.aliasValue}`;
    }

    // Send the GET request using HttpClient
    this.http.get(url).subscribe(
      (response: any) => {
        this.transactions = response.transactionList; // Assuming transactionList is returned in this case
        this.rowData = this.transactions; // Set the row data for the grid
        console.log('Transactions:', this.transactions); // Logging the result
      },
      (error) => {
        console.error('Error fetching transactions:', error);
      }
    );
  }
}
