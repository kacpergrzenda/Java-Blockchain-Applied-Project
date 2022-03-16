import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BlockchainService } from './blockchain.service';
import { TransactionsComponent } from './pages/transactions/transactions.component';
import { HomeComponent } from './pages/home/home.component';
import { MiningComponent } from './pages/mining/mining.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import { DialogWalletComponent } from './dialogs/dialog-wallet/dialog-wallet.component';
import {MatTooltipModule} from '@angular/material/tooltip';
import {ClipboardModule} from '@angular/cdk/clipboard';
import { DialogWalletConnectComponent } from './dialogs/dialog-wallet-connect/dialog-wallet-connect.component';
import {MatInputModule} from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
@NgModule({
  declarations: [
    AppComponent,
    TransactionsComponent,
    HomeComponent,
    MiningComponent,
    DialogWalletComponent,
    DialogWalletConnectComponent
  ],
  entryComponents: [DialogWalletComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatTooltipModule,
    ClipboardModule,
    MatInputModule,
    FormsModule,
    MatCardModule,
    MatDividerModule
  ],
  providers: [BlockchainService],
  bootstrap: [AppComponent]
})
export class AppModule { }
