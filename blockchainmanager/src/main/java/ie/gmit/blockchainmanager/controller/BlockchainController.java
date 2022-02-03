package ie.gmit.blockchainmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.gmit.blockchainmanager.service.BlockchainService;

@RestController
@RequestMapping("/Blockchain")
public class BlockchainController {
	private final BlockchainService bs;
	
	public BlockchainController(BlockchainService bs) {
		super();
		this.bs = bs;
	}

}
