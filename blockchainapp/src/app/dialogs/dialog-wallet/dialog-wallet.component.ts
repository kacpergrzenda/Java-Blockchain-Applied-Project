import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BlockchainService } from 'src/app/blockchain.service';
import { HttpErrorResponse } from '@angular/common/http';
import { DialogWalletConnectComponent } from '../dialog-wallet-connect/dialog-wallet-connect.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-wallet',
  templateUrl: './dialog-wallet.component.html',
  styleUrls: ['./dialog-wallet.component.scss']
})
export class DialogWalletComponent implements OnInit {

  public userKeys!: string[];
  privateKey: any;
  publicKey: any;
  balance: any;
  walletInformationDiv:boolean = false;

  constructor(private blockchainService: BlockchainService, 
              @Inject(MAT_DIALOG_DATA) public data: any, 
              public dialog: MatDialog
              ) { }

  ngOnInit(): void {
    this.checkIfWalletExists();
  }

  public createWallet(): void {
    this.blockchainService.createWallet().subscribe(
      (response: string[]) => {
        this.userKeys = response;
        /* Set keys to LocalStorage */
        localStorage.setItem("privatekey", this.userKeys[0]);
        localStorage.setItem("publickey", this.userKeys[1]);
        localStorage.setItem("balance", this.userKeys[2]);
        this.privateKey = localStorage.getItem("privatekey");
        this.publicKey = localStorage.getItem("publickey");
        this.balance = localStorage.getItem("balance");
        this.walletInformationDiv = true;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public checkIfWalletExists(): void {
    this.privateKey = localStorage.getItem("privatekey");
    this.publicKey = localStorage.getItem("publickey");
    this.balance = localStorage.getItem("balance");
    if (this.privateKey == null || this.publicKey == null) {
      this.walletInformationDiv = false;
    }
    else{
      this.walletInformationDiv = true;
    }
  }

  /*  Dialog */
  openDialogWalletConnect() {
    this.dialog.open(DialogWalletConnectComponent);
  }
}
