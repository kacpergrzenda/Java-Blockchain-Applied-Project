package ie.gmit.sw;

import java.util.ArrayList;

public class Blockchain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();

//	public Block genesisBlock() {
//		return new Block();
//	}

	public static void main(String[] args) {
		blockchain.add(new Block(0, "The Times Jan/03/2009 Chancellor on brink of second bailout for banks.", "0"));
		System.out.println(blockchain);
	}
	
}
