import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { ColDef } from 'ag-grid-community';
import { AuthService } from '../auth/auth.service';
import {AgGridAngular} from "ag-grid-angular";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-outgoing-transactions',
  templateUrl: './outgoing-transactions.component.html',
  styleUrls: ['./outgoing-transactions.component.css'],
  imports: [
    AgGridAngular,
    NgIf
  ],
  standalone: true
})
export class OutgoingTransactionsComponent implements OnInit {
  transactions: any[] = []; // Store fetched transactions
  rowData: any[] = [];
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
  themeClass = "ag-theme-quartz-dark";

  constructor(
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.fetchOutgoingTransactions();
  }

  fetchOutgoingTransactions() {
    this.searchAttempted = true; // Mark that search has been attempted
    const aliasType = this.authService.getAliasType()?.toLowerCase();
    const aliasValue = this.authService.getAliasValue();
    let url: string = `${environment.apiUrl}/transactions/outgoing/${aliasType}/${aliasValue}`;

    // Send the GET request using HttpClient
    this.http.get(url).subscribe(
      (response: any) => {
        this.transactions = response.transactionList; // Assuming transactionList is returned in this case
        this.rowData = this.transactions; // Set the row data for the grid
        console.log('Outgoing Transactions:', this.transactions); // Logging the result
      },
      (error) => {
        console.error('Error fetching outgoing transactions:', error);
      }
    );
  }
}
