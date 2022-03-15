export interface Block {
    id: number; 
    timestamp: string;
    previousHash: string;
    nonce: number;
    hash: string;
}