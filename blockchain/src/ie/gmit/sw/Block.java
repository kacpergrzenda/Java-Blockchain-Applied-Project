package ie.gmit.sw;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Block {
	private int index;
	private String timestamp;
	private String data;
	private String previousHash;
	private String hash;
	private int nonce;
	
	private Date date = new Date();  
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");  
	
	public Block(int index, String data, String previousHash) {
		this.index = index;
		this.timestamp = dateFormatter.format(date);
		this.data = data;
		this.previousHash = previousHash;
		this.hash = calculateHash(index, previousHash, timestamp, data);
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

	public String getData() {
		return data;
	}

	public String getHash() {
		return hash;
	}

	public String calculateHash(int index, String previousHash, String timestamp, String data)
	{
		String convertToHash = Integer.toString(index) + previousHash + this.timestamp + data + this.nonce;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(convertToHash.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < hash.length; i++) {
	            final String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) 
	              hexString.append('0');
	            hexString.append(hex);
	        }
			return hexString.toString();
		} catch(Exception ex){
			throw new RuntimeException(ex);
	    }
	}
	
	public void mineBlock(int difficulty) {
		System.out.println("Mining Block...");
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!this.hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			this.hash = calculateHash(this.index, this.previousHash, this.timestamp, this.data);
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	
	@Override
	public String toString() {
		return "Block [index=" + index + ", timestamp=" + timestamp + ", data=" + data + ", previousHash="
				+ previousHash + ", hash=" + hash + "]";
	}

}
