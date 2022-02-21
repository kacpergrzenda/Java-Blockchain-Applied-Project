package ie.gmit.blockchainmanager.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.gmit.blockchainmanager.model.BlockManager;
import ie.gmit.blockchainmanager.repo.BlockchainRepo;
import ie.gmit.sw.Block;
import ie.gmit.sw.Blockchain;

@Service
public class BlockchainService {
	private final BlockchainRepo br;
	private final Blockchain bc = new Blockchain(); /* Create a instance of the Blockchain from Blockchain class */
	@Autowired
	public BlockchainService(BlockchainRepo br) {
		super();
		this.br = br;
	}
	
	
	public Block addBlock(String newTransactionData) {	
		Block b = bc.addBlock(newTransactionData);
		//System.out.println("Service:" + bc.blockchain);
		BlockManager bm = new BlockManager( b.getTimestamp() , b.getTransactionData(), b.getPreviousHash(), b.getHash(), 0); // creates a copy of the existing block 
		br.save(bm); // saves a block into the mysql database
		System.out.println(bc.blockchain);
		return b;
	}
	
	public ArrayList<Block> getAllBlocks() {
		return bc.blockchain;
	}
}
