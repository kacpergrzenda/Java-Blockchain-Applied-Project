import { Component, OnInit } from '@angular/core';
import { BlockchainService } from 'src/app/blockchain.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Transaction } from 'src/app/transaction';

@Component({
  selector: 'app-mining',
  templateUrl: './mining.component.html',
  styleUrls: ['./mining.component.scss']
})
export class MiningComponent implements OnInit {

  allTransactions!: Transaction[]
  firstT!: Transaction
  privateKey: any;
  publicKey: any;

  constructor(private blockchainService: BlockchainService) { }

  ngOnInit(): void {
    //Check if there is any blocks that need to be mined
    this.getAllTransactions();
  }
  public getAllTransactions(): void {
    this.blockchainService.getAllTransactions().subscribe(
      (response: Transaction[]) => {
        this.allTransactions = response
        this.firstT = this.allTransactions[0]
        console.log(this.allTransactions);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

 

  public mineBlock() {
    this.privateKey = localStorage.getItem("privatekey");
    this.publicKey = localStorage.getItem("publickey");
    this.blockchainService.mineBlock(this.publicKey).subscribe(
      (response: any) => {
        console.log(response);
        this.getAllTransactions();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
