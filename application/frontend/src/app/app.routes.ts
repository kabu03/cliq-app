import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {TransactionsComponent} from './transactions/transactions.component';
import {AddTransactionComponent} from './add-transaction/add-transaction.component';
import {ErrorPageComponent} from './error-page/error-page.component';
import {RemoveTransactionComponent} from './remove-transaction/remove-transaction.component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'transactions', component: TransactionsComponent},
  {path: 'add-transaction', component: AddTransactionComponent},
  {path: 'remove-transaction', component: RemoveTransactionComponent},
  {path: '**', component: ErrorPageComponent},
];
