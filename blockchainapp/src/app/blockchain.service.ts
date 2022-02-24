import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Block } from './block';
import { environment } from 'src/environments/environment';
import { Wallet } from './wallet';
@Injectable({
  providedIn: 'root'
})
export class BlockchainService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  /* Gets Blocks from Blockchain*/
  public getBlockchain(): Observable<Block[]> {
    return this.http.get<Block[]>(`${this.apiServerUrl}/blockchain/all`);
  }

  /*  Create a new block by sending it transaction data*/
  public addBlock(transaction: string): Observable<string> {
    return this.http.post<string>(`${this.apiServerUrl}/blockchain/add`, transaction);
  }

  /*Generate a wallet*/
  public createWallet(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiServerUrl}/blockchain/createWallet`);
  }
}
