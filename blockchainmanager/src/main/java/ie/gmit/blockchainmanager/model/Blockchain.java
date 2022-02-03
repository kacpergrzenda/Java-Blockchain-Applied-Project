package ie.gmit.blockchainmanager.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import ie.gmit.sw.Block;

@Entity
public class Blockchain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private ArrayList<Block> blockchain = new ArrayList<Block>();

}
