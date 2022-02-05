package ie.gmit.blockchainmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.gmit.blockchainmanager.service.BlockchainService;
import ie.gmit.sw.Block;
import ie.gmit.sw.Blockchain;

@RestController
@RequestMapping("/blockchain")
public class BlockchainController {
	private final BlockchainService bs;
	
	public BlockchainController(BlockchainService bs) {
		super();
		this.bs = bs;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Block> addBlock(@RequestBody String s) {
		
		return new ResponseEntity<>(bs.addBlock(s), HttpStatus.CREATED);
	}

}
