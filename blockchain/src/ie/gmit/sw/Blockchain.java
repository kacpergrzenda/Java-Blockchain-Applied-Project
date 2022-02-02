package ie.gmit.sw;

import java.util.ArrayList;

public class Blockchain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	private int difficulty = 2;
	private int miningReward = 10;
	private ArrayList<String> pendingTransactions = new ArrayList<String>();
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.getHash().equals(currentBlock.calculateHash(currentBlock.getIndex(), currentBlock.getPreviousHash(), currentBlock.getTimestamp(), currentBlock.getTransactionData())) ){
				System.out.println("Current Hashes not equal: " + currentBlock.getHash());			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
	
	private String getLatestBlockHash() {
		return blockchain.get(blockchain.size()-1).getHash();
	}
	
//	public void minePendingTransactions(String minersHash) {
//		
//		blockchain.get(blockchain.size()-1).mineBlock(this.difficulty);
//	}
	
	public void addBlock(String newTransactionData) {
		this.pendingTransactions.add(newTransactionData); //add new transaction to pending transactions list
		Block b = new Block(blockchain.size(), this.pendingTransactions, getLatestBlockHash()); /// create a new black with the pending transactions
		b.mineBlock(this.difficulty); // MineTheBlock
		this.pendingTransactions.clear();
		blockchain.add(b); // Once block is mined add the new block to block-chain
	}
	
	public void createGenesisBlock() {
		this.pendingTransactions.add("The Times Jan/03/2009 Chancellor on brink of second bailout for banks.");
		Block b = new Block(blockchain.size(), this.pendingTransactions, "0");
		b.mineBlock(this.difficulty);
		this.pendingTransactions.clear();
		blockchain.add(b);
	}

	public static void main(String[] args) {
		Blockchain bc = new Blockchain();
		bc.createGenesisBlock(); // Genesis Block
		bc.addBlock("Second Block."); // Block 1
		bc.addBlock("third Block."); // Block 1


		//System.out.println(blockchain.get(2));


		
		System.out.println("How many blocks is there: " + blockchain.size());
		System.out.println("Is Blockchain Valid: " + isChainValid());
	}
	
}
