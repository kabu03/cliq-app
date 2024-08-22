import { Component } from '@angular/core';
import {TransactionSearchComponent} from "../transaction-search/transaction-search.component";

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [
    TransactionSearchComponent
  ],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent {

}
