package ie.gmit.blockchainmanager.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlockManager implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private String timestamp;
	private ArrayList<String> transactionData = new ArrayList<String>();
	private String previousHash;
	private String hash;
	private int nonce;
	
	public BlockManager()
	{}

	public BlockManager( String timestamp, ArrayList<String> transactionData, String previousHash,
			String hash, int nonce) {
		this.timestamp = timestamp;
		this.transactionData = transactionData;
		this.previousHash = previousHash;
		this.hash = hash;
		this.nonce = nonce;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public ArrayList<String> getTransactionData() {
		return transactionData;
	}
	public void setTransactionData(ArrayList<String> transactionData) {
		this.transactionData = transactionData;
	}
	public String getPreviousHash() {
		return previousHash;
	}
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getNonce() {
		return nonce;
	}
	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	


	
	
	

}
