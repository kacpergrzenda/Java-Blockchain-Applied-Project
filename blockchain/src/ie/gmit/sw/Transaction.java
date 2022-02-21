package ie.gmit.sw;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

public class Transaction {

	public String transactionId;
	public PublicKey sender; // senders address/public key.
	public PublicKey reciepient; // Recipients address/public key.
	public int amount;
	public byte[] signature; // Prove the owner of sending wallet is authentic

	// To prove receivers and senders current balance;
	//public ArrayList<TransactionInput> senderBalance = new ArrayList<TransactionInput>();
	//public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

	private int hashChanger = 0;

	public Transaction(PublicKey from, PublicKey to, int amount) {
		this.sender = from;
		this.reciepient = to;
		this.amount = amount;

	}

	// This Calculates the transaction hash (which will be used as its Id)
	private String calculateTransactionHash() {
		hashChanger++; //increase the sequence to avoid 2 identical transactions having the same hash
		String convertToHash = getStringFromKey(sender) + getStringFromKey(reciepient) + Integer.toString(amount) + hashChanger;
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
	
	public String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
}
