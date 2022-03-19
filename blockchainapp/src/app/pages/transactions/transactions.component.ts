import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { BlockchainService } from 'src/app/blockchain.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Transaction } from '../../transaction';
@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.scss']
})
export class TransactionsComponent implements OnInit {

  transactionForm: any;

  //Getter/Accessors
  get senderControl() {
    return this.transactionForm.get('sender');
  }

  get receiverControl() {
    return this.transactionForm.get('receiver');
  }

  get amountControl() {
    return this.transactionForm.get('amount');
  }


  constructor(private blockchainService: BlockchainService) { }

  ngOnInit(): void {

    this.transactionForm = new FormGroup({
      sender: new FormControl('', [Validators.required]),
      receiver: new FormControl('', [Validators.required]),
      amount: new FormControl('', [Validators.required]),
    });

  }


  onTransaction() {
    this.blockchainService.createTransaction(this.senderControl.value, this.receiverControl.value, this.amountControl.value).subscribe(
      (response: string) => {
        console.log(response)
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


}
