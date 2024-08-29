import {Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import {HttpClient, provideHttpClient} from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import {environment} from "../../environments/environment";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {AuthService} from "../auth/auth.service";

@Component({
  standalone: true,
  selector: 'app-add-transaction',
  templateUrl: './add-transaction.component.html',
  styleUrl: './add-transaction.component.scss',
  imports: [ReactiveFormsModule, MatFormField, MatLabel, MatSelect, MatOption, MatButton, MatInput]
})
export class AddTransactionComponent implements OnInit {
  transactionForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private authService: AuthService // Inject AuthService to get logged-in user info
  ) {
    this.transactionForm = this.fb.group({
      debtorAliasType: ['', Validators.required],  // We will set this programmatically
      debtorAliasValue: ['', Validators.required], // We will set this programmatically
      creditorAliasType: ['', Validators.required],
      creditorAliasValue: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0.01)]],
      currency: ['', Validators.required],
      purpose: ['', Validators.required],
      comments: [''],
    });
  }

  ngOnInit() {
    // Automatically set the debtor's alias type and value based on the logged-in user
    this.transactionForm.patchValue({
      debtorAliasType: this.authService.getAliasType(),
      debtorAliasValue: this.authService.getAliasValue()
    });
  }

  onSubmit() {
    if (this.transactionForm.valid) {
      const url = `${environment.apiUrl}/transactions/add-transaction`;
      this.http.post<Response>(url, this.transactionForm.value)
        .pipe(
          catchError(error => {
            console.error('Error occurred:', error);
            this.snackBar.open('Transaction failed. Please try again.', 'Close', { duration: 3000 });
            return of(null);
          })
        )
        .subscribe((response: any) => {
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
