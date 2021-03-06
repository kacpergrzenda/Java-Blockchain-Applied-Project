package ie.gmit.sw.transaction;
import java.security.*;
import java.util.ArrayList;

import ie.gmit.sw.Blockchain;
import ie.gmit.sw.BlockchainCryptography;

public class Transaction {

	public String transactionId;
	public PublicKey sender; 
	public PublicKey reciepient; 
	public float amount;
	public byte[] signature; // Prove the owner of sending wallet is authentic

	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

	private int hashChanger = 0;

	public Transaction(PublicKey from, PublicKey to, float amount, ArrayList<TransactionInput> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.amount = amount;
		this.inputs = inputs;

	}

	/* Calculate transaction hash */
	private String calculateTransactionHash() {
		hashChanger++; //increase the sequence to avoid 2 identical transactions having the same hash
		String convertToHash = BlockchainCryptography.getStringFromKey(sender) + BlockchainCryptography.getStringFromKey(reciepient) + Float.toString(amount) + hashChanger;
		return BlockchainCryptography.convertToHash(convertToHash);
	}
	
	/* Generate a Digital Signature of the transaction*/
	public void generateSignature(PrivateKey privateKey) {
		String data = BlockchainCryptography.getStringFromKey(sender) + BlockchainCryptography.getStringFromKey(reciepient) + Float.toString(amount)	;
		signature = BlockchainCryptography.applySignature(privateKey,data);		
	}
	
	/* Verifies the data we signed hasnt been tampered with */
	public boolean verifiySignature() {
		String data = BlockchainCryptography.getStringFromKey(sender) + BlockchainCryptography.getStringFromKey(reciepient) + Float.toString(amount)	;
		return BlockchainCryptography.verifySignature(sender, data, signature);
	}
	
	/* returns sum of inputs(UTXOs) values */
	public float getInputsValue() {
		float total = 0;
		for(TransactionInput i : inputs) {
			if(i.unspentTXOutput == null) continue; //if Transaction can't be found skip it, This behavior may not be optimal.
			total += i.unspentTXOutput.value;
		}
		return total;
	}
	
	/* Gets transaction spent amount */
	public float getOutputsValue() {
		float total = 0;
		for(TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}
	
	/* Verify Transaction */
	public boolean processTransaction() {
		
		if(verifiySignature() == false) {
			System.out.println("Transaction Signature failed to verify");
			return false;
		}
		
		for(TransactionInput i : inputs) {
			i.unspentTXOutput = Blockchain.UTXOs.get(i.transactionOutputId);
		}
		
		//Checks if transaction is valid:
		if(getInputsValue() < 0.1f) {
			System.out.println("Transaction Inputs too small: " + getInputsValue());
			System.out.println("Please enter the amount greater than " + 0.1f);
			return false;
		}
		
		//Generate transaction outputs:
		float leftOver = getInputsValue() - amount; //get value of inputs then the left over change:
		transactionId = calculateTransactionHash();
		outputs.add(new TransactionOutput( this.reciepient, amount,transactionId)); //send value to recipient
		outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender	
		
		//Add outputs to Unspent list
		for(TransactionOutput o : outputs) {
			Blockchain.UTXOs.put(o.id , o);
		}
		
		//Remove transaction inputs from UTXO lists as spent:
		for(TransactionInput i : inputs) {
			if(i.unspentTXOutput == null) continue; //if Transaction can't be found skip it 
			Blockchain.UTXOs.remove(i.unspentTXOutput.id);
		}
		
		return true; 
	}
	
		
}
