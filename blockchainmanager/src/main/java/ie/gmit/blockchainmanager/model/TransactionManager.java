package ie.gmit.blockchainmanager.model;

public class TransactionManager {
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
