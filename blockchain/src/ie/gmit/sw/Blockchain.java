package ie.gmit.sw;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import ie.gmit.sw.transaction.Transaction;
import ie.gmit.sw.transaction.TransactionOutput;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import com.google.gson.GsonBuilder;
public class Blockchain {
	
	public ArrayList<Block> blockchain = new ArrayList<Block>();
	private int difficulty = 2;
	private int miningReward = 10;
	private ArrayList<String> pendingTransactions = new ArrayList<String>();
	public static ArrayList<Wallet> wallets = new ArrayList<Wallet>();
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions.
	
	public static Wallet walletA;
	public static Wallet walletB;
	
	/* Checks if the Blockchain is valid by running a consesus by all the blocks in the chain */
	public Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		
		//loop through block-chain to check hashes:
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
		if(blockchain.size() <= 0) {
			return "0";
		}else{
			return blockchain.get(blockchain.size()-1).getHash();
		}
	}
	
//	public void minePendingTransactions(String minersHash) {
//		
//		blockchain.get(blockchain.size()-1).mineBlock(this.difficulty);
//	}
	
	public Block addBlock(String newTransactionData) {
		this.pendingTransactions.add(newTransactionData); //add new transaction to pending transactions list
		
		//add the transaction from transaction class^^^^
		
		/* ADD HERE A POTENRTIAL BLOCKER TO NOT ADD THE BLOCK UNTIL MINING HAPPENS */
		
		/// create a new black with the pending transactions
		Block b = new Block(blockchain.size(), this.pendingTransactions, getLatestBlockHash());
		b.mineBlock(this.difficulty); // MineTheBlock
		this.pendingTransactions.clear();
		blockchain.add(b); // Once block is mined add the new block to block-chain
		return b;
	}
	
	
	public void createGenesisBlock() {
		this.pendingTransactions.add("The Times Jan/03/2009 Chancellor on brink of second bailout for banks.");
		Block b = new Block(blockchain.size(), this.pendingTransactions, "0");
		b.mineBlock(this.difficulty);
		this.pendingTransactions.clear();
		blockchain.add(b);
	}

//	public static void main(String[] args) {
//		Blockchain bc = new Blockchain();
//		bc.addBlock("1"); // Genesis Block
//		bc.addBlock("Second Block."); // Block 1
//		bc.addBlock("third Block."); // Block 1
//
//		System.out.println(bc.blockchain.get(0));
//		System.out.println("How many blocks is there: " + bc.blockchain.size());
//		System.out.println("Is Blockchain Valid: " + bc.isChainValid());
//	}
	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider()); 
		
		walletA = new Wallet();
		walletB = new Wallet();
		
		System.out.println("Private and public keys:");
		System.out.println(BlockchainCryptography.getStringFromKey(walletA.privateKey));
		System.out.println(BlockchainCryptography.getStringFromKey(walletA.publicKey));
		
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature());
	}
	
}
