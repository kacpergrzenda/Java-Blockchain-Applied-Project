import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Block } from 'src/app/block';
import { BlockchainService } from 'src/app/blockchain.service';
import { Wallet } from 'src/app/wallet';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public blocks!: Block[];
  public userWallet!: Wallet; 
  public uw!: string[];
  div1:boolean = false;
  constructor(private blockchainService: BlockchainService){}

  /* On initialise of the app run this */
  ngOnInit(): void {
      this.getBlockchain();
      //this.createWallet();
  }

  public getBlockchain(): void {
    this.blockchainService.getBlockchain().subscribe(
      (response: Block[]) => {
        this.blocks = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public createWallet(): void {
    this.blockchainService.createWallet().subscribe(
      (response: string[]) => {
        this.uw = response;
        this.div1 = true;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
