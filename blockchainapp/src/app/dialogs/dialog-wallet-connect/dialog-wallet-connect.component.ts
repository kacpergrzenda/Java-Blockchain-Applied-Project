import { Component, OnInit } from '@angular/core';
import { BlockchainService } from 'src/app/blockchain.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-dialog-wallet-connect',
  templateUrl: './dialog-wallet-connect.component.html',
  styleUrls: ['./dialog-wallet-connect.component.scss']
})
export class DialogWalletConnectComponent implements OnInit {
  value:any
  public userKeys!: string[];
  constructor(private blockchainService: BlockchainService) { }

  ngOnInit(): void {
  }

  public getWallet(pk :string): void {
    console.log(pk);
    this.blockchainService.getWallet(pk).subscribe(
      (response: any) => {
        this.userKeys = response;
        /* Set keys to LocalStorage */
        localStorage.setItem("privatekey", this.userKeys[0]);
        localStorage.setItem("publickey", this.userKeys[1]);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
