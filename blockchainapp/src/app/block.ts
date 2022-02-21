export interface Block {
    id: number;
    hash: string;
    nonce: number;
    previousHash: string;
    timestamp: string;
    transactionData: any;
}