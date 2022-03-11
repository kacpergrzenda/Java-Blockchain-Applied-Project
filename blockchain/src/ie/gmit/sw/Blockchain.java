package ie.gmit.sw;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import ie.gmit.sw.transaction.Transaction;
import ie.gmit.sw.transaction.TransactionInput;
import ie.gmit.sw.transaction.TransactionOutput;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import com.google.gson.GsonBuilder;
public class Blockchain {
	
	public ArrayList<Block> blockchain = new ArrayList<Block>();
	private int difficulty = 2;
	public static ArrayList<Wallet> wallets = new ArrayList<Wallet>();
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions.
	
	public static Wallet walletA;
	public static Wallet walletB;
	public Wallet coinBase = new Wallet();;
	public Transaction genesisTransaction;
	private Block genesis;
	/* Checks if the Blockchain is valid by running a consesus by all the blocks in the chain */
	public Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
		//tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		
		
		//loop through block-chain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			String hashTarget = new String(new char[difficulty]).replace('\0', '0');
			//compare registered hash and calculated hash:
			if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal: " + currentBlock.getHash());			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			
			//check if hash is solved
			if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}
			
			//loop thru blockchains transactions:
			TransactionOutput tempOutput;
			for(int j=0; j <currentBlock.transactions.size(); j++) {
				Transaction currentTransaction = currentBlock.transactions.get(j);
				
				if(!currentTransaction.verifiySignature()) {
					System.out.println("#Signature on Transaction(" + j + ") is Invalid");
					return false; 
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("#Inputs are note equal to outputs on Transaction(" + j + ")");
					return false; 
				}
				for(TransactionInput input: currentTransaction.inputs) {	
					tempOutput = tempUTXOs.get(input.transactionOutputId);
					
					if(tempOutput == null) {
						System.out.println("#Referenced input on Transaction(" + j + ") is Missing");
						return false;
					}
					
					if(input.unspentTXOutput.value != tempOutput.value) {
						System.out.println("#Referenced input Transaction(" + j + ") value is Invalid");
						return false;
					}
					
					tempUTXOs.remove(input.transactionOutputId);
				}
				
				for(TransactionOutput output: currentTransaction.outputs) {
					tempUTXOs.put(output.id, output);
				}
				
				if( currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
					System.out.println("#Transaction(" + j + ") output reciepient is not who it should be");
					return false;
				}
				if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
					System.out.println("#Transaction(" + j + ") output 'change' is not sender.");
					return false;
				}
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
	
	public Block addBlock() {
		if(blockchain.size() <= 0)
		{
			this.genesis.mineBlock(this.difficulty); // MineTheBlock
			blockchain.add(this.genesis); // Once block is mined add the new block to block-chain
			return this.genesis;
		}
		else
		{
			Block b = new Block(blockchain.size(), getLatestBlockHash());
			b.mineBlock(this.difficulty); // MineTheBlock
			blockchain.add(b); // Once block is mined add the new block to block-chain
			return b;
		}		
	}
	
	
//	public void createGenesisBlock() {
//		this.pendingTransactions.add("The Times Jan/03/2009 Chancellor on brink of second bailout for banks.");
//		Block b = new Block(blockchain.size(), this.pendingTransactions, "0"); 
//		b.mineBlock(this.difficulty);
//		this.pendingTransactions.clear();
//		blockchain.add(b);
//	}
	
	public void createGenesisBlock() {
		genesisTransaction = new Transaction(this.coinBase.publicKey, this.walletA.publicKey, 1000f, null);
		genesisTransaction.generateSignature(this.coinBase.privateKey);	 //manually sign the genesis transaction	
		genesisTransaction.transactionId = "0"; //manually set the transaction id
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.amount, genesisTransaction.transactionId)); //manually add the Transactions Output
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.
		this.genesis = new Block(blockchain.size(), getLatestBlockHash());
		genesis.addTransaction(genesisTransaction);
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
//	public static void main(String[] args) {
//		Security.addProvider(new BouncyCastleProvider()); 
//		
//		walletA = new Wallet();
//		walletB = new Wallet();
//		
//		System.out.println("Private and public keys:");
//		System.out.println(BlockchainCryptography.getStringFromKey(walletA.privateKey));
//		System.out.println(BlockchainCryptography.getStringFromKey(walletA.publicKey));
//		
//		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
//		transaction.generateSignature(walletA.privateKey);
//		//Verify the signature works and verify it from the public key
//		System.out.println("Is signature verified");
//		System.out.println(transaction.verifiySignature());
//	}
	
	public static void main(String[] args) {
		Blockchain bc = new Blockchain();
		//Create wallets:
//		walletA = new Wallet();
		walletB = new Wallet();
		walletA = new Wallet();
		bc.createGenesisBlock();
		bc.addBlock();
		System.out.println("Is Chain Valid: " + bc.isChainValid());
		System.out.println("Gensis Balance: " + bc.coinBase.getBalance());
		
		
		Block block1 = new Block(bc.blockchain.size(), bc.getLatestBlockHash());
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		bc.addBlock();
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		
		Block block2 = new Block(bc.blockchain.size(), bc.getLatestBlockHash());
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
		bc.addBlock();
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		
		Block block3 = new Block(bc.blockchain.size(), bc.getLatestBlockHash());
		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds( walletA.publicKey, 20));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		
		System.out.println(bc.isChainValid());
	}
}
