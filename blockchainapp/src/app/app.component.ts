import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Block } from './block';
import { BlockchainService } from './blockchain.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'blockchainapp';
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
