import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Block } from 'src/app/block';
import { BlockchainService } from 'src/app/blockchain.service';
import { Wallet } from 'src/app/wallet';
import { MatDialog } from '@angular/material/dialog';
import { DialogWalletComponent } from 'src/app/dialogs/dialog-wallet/dialog-wallet.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public blocks!: Block[];
  public userWallet!: Wallet; 
  public userKeys!: string[];
  div1:boolean = false;
  constructor(private blockchainService: BlockchainService, public dialog: MatDialog){}

  /* On initialise of the app run this */
  ngOnInit(): void {
      this.getBlockchain();
      //this.createWallet();
  }

  public getBlockchain(): void {
    this.blockchainService.getBlockchain().subscribe(
      (response: Block[]) => {
        this.blocks = response;
        
        
        console.log(this.blocks);
        console.log("1: " + this.blocks[1].transactions[0].transactionId)
        console.log("2: " + this.blocks[0].transactions)
        //console.log(response)
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  


  /*  Dialog */
  openDialogWallet() {
    this.dialog.open(DialogWalletComponent, {data:{userKeys: "this.userKeys"}});
  }
}
