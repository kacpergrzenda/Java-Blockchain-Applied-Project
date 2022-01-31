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
	
	private Date date = new Date();  
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");  
	
	public Block(int index, String data, String previousHash) {
		this.index = index;
		this.timestamp = dateFormatter.format(date);
		this.data = data;
		this.previousHash = previousHash;
		this.hash = calculateHash(index, previousHash, timestamp, data);
	}
	
	private String calculateHash(int index, String previousHash, String timestamp, String data)
	{
		String convertToHash = Integer.toString(index) + previousHash + timestamp + data;
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
	
	@Override
	public String toString() {
		return "Block [index=" + index + ", timestamp=" + timestamp + ", data=" + data + ", previousHash="
				+ previousHash + ", hash=" + hash + "]";
	}

	public static void main(String[] args) {
		Block b = new Block(0, "hello", "test");
		System.out.println(b.toString());
	}

}
