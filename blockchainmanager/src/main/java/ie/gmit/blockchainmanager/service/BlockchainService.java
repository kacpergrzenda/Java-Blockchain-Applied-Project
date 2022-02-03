package ie.gmit.blockchainmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.gmit.blockchainmanager.repo.BlockchainRepo;

@Service
public class BlockchainService {
	private final BlockchainRepo br;
	
	@Autowired
	public BlockchainService(BlockchainRepo br) {
		super();
		this.br = br;
	}
}
