package ie.gmit.blockchainmanager.model;

import java.io.Serializable;

public class TransactionManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String transactionId;
	public String sender;
	public String reciepient;
	public float amount;
	
	public TransactionManager(String transactionId, String sender, String reciepient, float amount) {
		super();
		this.transactionId = transactionId;
		this.sender = sender;
		this.reciepient = reciepient;
		this.amount = amount;
	}
}
