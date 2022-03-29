package ie.gmit.blockchainmanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class BlockManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private String timestamp;
	private String previousHash;
	private int nonce;
	private String merkleRoot;
	private String hash;
	private int num;
	public ArrayList<TransactionManager> transactions = new ArrayList<TransactionManager>();
	
	public BlockManager()
	{}
	
	public BlockManager(int num, String timestamp, String previousHash, int nonce, String merkleRoot, String hash, ArrayList<TransactionManager> transactions) {
		super();
		this.num = num;
		this.timestamp = timestamp;
		this.previousHash = previousHash;
		this.nonce = nonce;
		this.merkleRoot = merkleRoot;
		this.hash = hash;
		this.transactions.addAll(transactions);
		
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getId() {
	return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMerkleRoot() {
		return merkleRoot;
	}

	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}

	public int getNumber() {
		return num;
	}

	public void setNumber(int num) {
		this.num = num;
	}
	

	
	
	

}
