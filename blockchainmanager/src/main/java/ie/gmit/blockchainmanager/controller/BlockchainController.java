package ie.gmit.blockchainmanager.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/all")
	public ResponseEntity<ArrayList<Block>> getAllBlocks() {
		return new ResponseEntity<>(bs.getAllBlocks(), HttpStatus.OK);
	}
	
	/* When creating a new block all you pass is the transaction data because the blockchain creates adn knows the rest */
	@PostMapping("/add")
	public ResponseEntity<Block> addBlock(@RequestBody String s) {
		return new ResponseEntity<>(bs.addBlock(s), HttpStatus.CREATED);
	}
	
	

}
