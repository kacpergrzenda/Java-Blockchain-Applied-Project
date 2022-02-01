package ie.gmit.sw;

import java.util.ArrayList;

public class Blockchain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 2;
//	public Block genesisBlock() {
//		return new Block();
//	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.getHash().equals(currentBlock.calculateHash(currentBlock.getIndex(), currentBlock.getPreviousHash(), currentBlock.getTimestamp(), currentBlock.getData())) ){
				System.out.println("Current Hashes not equal");			
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
	
	public void addBlock(String data) {
		blockchain.add(new Block(blockchain.size(), data, getLatestBlockHash()));
		
		blockchain.get(blockchain.size()-1).mineBlock(difficulty);
	}
	
	public void createGenesisBlock() {
		blockchain.add(new Block(blockchain.size(), "The Times Jan/03/2009 Chancellor on brink of second bailout for banks.", "0"));
		blockchain.get(blockchain.size()-1).mineBlock(difficulty);
	}

	public static void main(String[] args) {
		Blockchain bc = new Blockchain();
		bc.createGenesisBlock();
		bc.addBlock("Second Block.");
		//System.out.println(blockchain.get(blockchain.size()-1));
		//blockchain.add(new Block(blockchain.size(), "The Times Jan/03/2009 Chancellor on brink of second bailout for banks.", "0"));
		//blockchain.get(0).mineBlock(difficulty);
		
		//blockchain.add(new Block(blockchain.size(), "Second Block.", getLatestBlockHash()));
		//blockchain.get(1).mineBlock(difficulty);
		
		//blockchain.add(new Block(blockchain.size(), "Thrid Block.", getLatestBlockHash()));
		//blockchain.get(1).mineBlock(difficulty);
		
		System.out.println("How many blocks is there: " + blockchain.size());
		System.out.println("Is Blockchain Valid: " + isChainValid());
	}
	
}
