import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {TransactionsComponent} from './transactions/transactions.component';
import {AddTransactionComponent} from './add-transaction/add-transaction.component';
import {ErrorPageComponent} from './error-page/error-page.component';
import {RemoveTransactionComponent} from './remove-transaction/remove-transaction.component';
import {AuthGuard} from "./auth.guard";
import {LoginComponent} from "./login/login.component";

export const routes: Routes = [
  {path: '', component: HomeComponent, data: {title: 'Home - Cliq App'}},
  {path: 'transactions', component: TransactionsComponent, data: {title: 'Transactions'}},
  {path: 'login', component: LoginComponent, data: {title: 'Login'}},
  {path: 'add-transaction', component: AddTransactionComponent, data: {title: 'Add Transaction'}, canActivate: [AuthGuard]},
  {path: 'remove-transaction', component: RemoveTransactionComponent, data: {title: 'Remove Transaction'}, canActivate: [AuthGuard]},
  {path: '**', data: {title: 'Error 404'}, component: ErrorPageComponent},
];
