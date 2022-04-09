package ie.gmit.blockchainmanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlockClone implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private String timestamp;
	private String previousHash;
	private int nonce;
	private String hash;
	private int num;
	
	public BlockClone ()
	{}
	
	public BlockClone(int num, String timestamp, String previousHash, int nonce, String hash)
	{
		super();
		this.num = num;
		this.timestamp = timestamp;
		this.previousHash = previousHash;
		this.nonce = nonce;
		this.hash = hash;
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

	public int getNumber() {
		return num;
	}

	public void setNumber(int num) {
		this.num = num;
	}
}
