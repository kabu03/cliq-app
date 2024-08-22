import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import {environment} from "../../environments/environment";

@Component({
  standalone: true,
  selector: 'app-add-transaction',
  templateUrl: './add-transaction.component.html',
  imports: [ReactiveFormsModule, HttpClientModule], // Add HttpClientModule here
})
export class AddTransactionComponent {
  transactionForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private snackBar: MatSnackBar) {
    this.transactionForm = this.fb.group({
      debtorAliasType: ['', Validators.required],
      debtorAliasValue: ['', Validators.required],
      creditorAliasType: ['', Validators.required],
      creditorAliasValue: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0.01)]],
      currency: ['', Validators.required],
      purpose: ['', Validators.required],
      comments: [''],
    });
  }

  onSubmit() {
    if (this.transactionForm.valid) {
      let url:string = `${environment.apiUrl}`;
      this.http.post<Response>(url + '/transactions/add-transaction', this.transactionForm.value)
        .pipe(
          catchError(error => {
            console.error('Error occurred:', error);
            this.snackBar.open('Transaction failed. Please try again.', 'Close', { duration: 3000 });
            return of(null);
          })
        )
        .subscribe((response:any) => {
          if (response) {
            this.snackBar.open('Transaction added successfully with ID ' + response.transactionId + ". Don't forget the ID!", 'Close', { duration: 5000 });
          } else {
            this.snackBar.open('Transaction failed.', 'Close', { duration: 3000 });
          }
        });
    } else {
      this.snackBar.open('Form is invalid. Please correct the errors and try again.', 'Close', { duration: 3000 });
    }
  }
}
