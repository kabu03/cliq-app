import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import {HttpClientModule} from "@angular/common/http";

@Component({
  selector: 'app-remove-transaction',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule
  ],
  templateUrl: './remove-transaction.component.html',
  styleUrl: './remove-transaction.component.css'
})

export class RemoveTransactionComponent {
  transactionId!: number;

  constructor(private http: HttpClient, private snackBar: MatSnackBar) {}

  onRemove() {
    if (confirm("Are you sure you want to remove this transaction?")) {
      let url = `${environment.apiUrl}/transactions/${this.transactionId}`;

      this.http.delete(url).subscribe(
        (response: any) => {
          this.snackBar.open('Status: ' + response.status, 'Close', { duration: 3000 });
        },
        (error) => {
          console.error('Error removing transaction:', error);
          this.snackBar.open('Transaction removal failed. Please try again.', 'Close', { duration: 3000 });
        }
      );
    }
  }
}

