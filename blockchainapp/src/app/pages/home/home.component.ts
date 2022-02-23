import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Block } from 'src/app/block';
import { BlockchainService } from 'src/app/blockchain.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public blocks!: Block[];

  constructor(private blockchainService: BlockchainService){}

  /* On initialise of the app run this */
  ngOnInit(): void {
      this.getBlockchain();
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
}
