package ie.gmit.sw;

import java.util.ArrayList;

public class Blockchain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();

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
	
	public static String getLatestBlockHash() {
		return blockchain.get(blockchain.size()-1).getHash();
	}

	public static void main(String[] args) {
		blockchain.add(new Block(0, "The Times Jan/03/2009 Chancellor on brink of second bailout for banks.", "0"));
		blockchain.add(new Block(1, "Second Block.", getLatestBlockHash()));
		System.out.println(blockchain.size());
		System.out.println(isChainValid());
	}
	
}
