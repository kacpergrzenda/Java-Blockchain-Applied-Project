package ie.gmit.sw;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ie.gmit.sw.transaction.Transaction;

public class Block {
	private int index;
	private String timestamp;
	private ArrayList<String> transactionData = new ArrayList<String>();
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
	private String previousHash;
	private String hash;
	private int nonce;

	private Date date = new Date();  
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");  

	public Block(int index, ArrayList<String> transactionData, String previousHash) {
		this.index = index;
		this.timestamp = dateFormatter.format(date);
		//this.transactionData.addAll(transactionData);
		this.previousHash = previousHash;
		this.nonce = 0;
		this.hash = calculateHash();
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public int getIndex() {
		return index;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public ArrayList<String> getTransactionData() {
		return transactionData;
	}

	public String getHash() {
		return hash;
	}

	public String calculateHash()
	{
		String convertToHash = Integer.toString(this.index) + this.previousHash + this.timestamp + this.merkleRoot + this.nonce;
		return BlockchainCryptography.convertToHash(convertToHash);
	}

	public void mineBlock(int difficulty) {
		System.out.println("Mining Block...");
		this.merkleRoot = BlockchainCryptography.getMerkleRoot(transactions);
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!this.hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			this.hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	
	//Add transactions to this block
		public boolean addTransaction(Transaction transaction) {
			//process transaction and check if valid, unless block is genesis block then ignore.
			if(transaction == null) return false;		
			if((!"0".equals(previousHash))) {
				if((transaction.processTransaction() != true)) {
					System.out.println("Transaction failed to process. Discarded.");
					return false;
				}
			}

			transactions.add(transaction);
			System.out.println("Transaction Successfully added to Block");
			return true;
		}

}
