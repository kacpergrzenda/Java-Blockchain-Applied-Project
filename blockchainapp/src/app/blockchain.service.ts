import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Block } from './block';
import { environment } from 'src/environments/environment';
import { Wallet } from './wallet';
import { Transaction } from './transaction';
@Injectable({
  providedIn: 'root'
})
export class BlockchainService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      "Access-Control-Allow-Origin": "*",
      
    } ),responseType: 'text' as 'json'
  };

  /* Gets Blocks from Blockchain*/
  public getBlockchain(): Observable<Block[]> {
    return this.http.get<Block[]>(`${this.apiServerUrl}/blockchain/all`);
  }

  /*Generate a wallet*/
  public createWallet(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiServerUrl}/blockchain/createWallet`);
  }

  /*Get Wallet Balance */
  public getBalance(pk: string): Observable<number > {
    return this.http.post<number>(`${this.apiServerUrl}/blockchain/getBalance`, pk);
  }

  /*Recover Wallet with Private Key */
  public getWallet(pk: string): Observable<string[]> {
    return this.http.post<string[]>(`${this.apiServerUrl}/blockchain/getWallet/`, pk);
  }

  /* Get all Transactions from current Block */
  public getAllTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiServerUrl}/blockchain/transactionList`);
  }

  /* Mine Block */
  public mineBlock(pk: string): Observable<string>{
    return this.http.post<string>(`${this.apiServerUrl}/blockchain/mineBlock/`, pk, this.httpOptions);
  }

  public createTransaction(sender: string, reciepient: string, amount: string): Observable<string>{
    return this.http.post<string>(`${this.apiServerUrl}/blockchain/createTransaction/`, {transactionId: 0,sender, reciepient, amount}, this.httpOptions);
  }
}
