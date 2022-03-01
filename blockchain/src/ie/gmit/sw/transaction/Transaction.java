package ie.gmit.sw.transaction;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

import ie.gmit.sw.BlockchainCryptography;

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
		String convertToHash = BlockchainCryptography.getStringFromKey(sender) + BlockchainCryptography.getStringFromKey(reciepient) + Integer.toString(amount) + hashChanger;
		return BlockchainCryptography.convertToHash(convertToHash);
	}
	
	//Signs all the data we dont wish to be tampered with.
	public void generateSignature(PrivateKey privateKey) {
		String data = BlockchainCryptography.getStringFromKey(sender) + BlockchainCryptography.getStringFromKey(reciepient) + Float.toString(amount)	;
		signature = BlockchainCryptography.applySignature(privateKey,data);		
	}
	
	//Verifies the data we signed hasnt been tampered with
	public boolean verifiySignature() {
		String data = BlockchainCryptography.getStringFromKey(sender) + BlockchainCryptography.getStringFromKey(reciepient) + Float.toString(amount)	;
		return BlockchainCryptography.verifySignature(sender, data, signature);
	}
	
}
