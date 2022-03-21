import { Transaction } from "./transaction";

export interface Block {
    number: any; 
    timestamp: string;
    previousHash: string;
    nonce: number;
    merkleRoot: String;
    hash: string;
    transactions: any;
}