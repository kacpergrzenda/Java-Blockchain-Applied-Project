import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BlockchainService } from 'src/app/blockchain.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-dialog-wallet',
  templateUrl: './dialog-wallet.component.html',
  styleUrls: ['./dialog-wallet.component.scss']
})
export class DialogWalletComponent implements OnInit {

  public userKeys!: string[];
  privateKey: any;
  publicKey: any;
  walletInformationDiv:boolean = false;

  constructor(private blockchainService: BlockchainService, @Inject(MAT_DIALOG_DATA) public data: any) { }

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
        this.privateKey = localStorage.getItem("privatekey");
        this.publicKey = localStorage.getItem("publickey");
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
    if (this.privateKey == null || this.publicKey == null) {
      this.walletInformationDiv = false;
    }
    else{
      this.walletInformationDiv = true;
    }
  }
}