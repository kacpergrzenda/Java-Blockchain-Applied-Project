package ie.gmit.sw;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Block {
	private int index;
	private String timestamp;
	private ArrayList<String> transactionData = new ArrayList<String>();
	private String previousHash;
	private String hash;
	private int nonce;

	private Date date = new Date();  
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");  

	public Block(int index, ArrayList<String> transactionData, String previousHash) {
		this.index = index;
		this.timestamp = dateFormatter.format(date);
		this.transactionData.addAll(transactionData);
		this.previousHash = previousHash;
		this.hash = calculateHash(index, previousHash, timestamp, transactionData);
		this.nonce = 0;
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

	public String calculateHash(int index, String previousHash, String timestamp, ArrayList<String> transactionData)
	{
		String convertToHash = Integer.toString(index) + previousHash + this.timestamp + transactionData + this.nonce;
		return BlockchainCryptography.convertToHash(convertToHash);
	}

	public void mineBlock(int difficulty) {
		System.out.println("Mining Block...");
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!this.hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			this.hash = calculateHash(this.index, this.previousHash, this.timestamp, this.transactionData);
		}
		System.out.println("Block Mined!!! : " + hash);
	}

}
