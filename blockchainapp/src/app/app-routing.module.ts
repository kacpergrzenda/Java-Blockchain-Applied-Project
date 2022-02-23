import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MiningComponent } from './pages/mining/mining.component';
import { TransactionsComponent } from './pages/transactions/transactions.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'mining', component: MiningComponent},
  {path: 'transactions', component: TransactionsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
