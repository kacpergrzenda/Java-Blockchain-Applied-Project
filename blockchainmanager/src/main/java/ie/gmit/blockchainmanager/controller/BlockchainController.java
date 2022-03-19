package ie.gmit.blockchainmanager.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.gmit.blockchainmanager.model.TransactionManager;
import ie.gmit.blockchainmanager.service.BlockchainService;
import ie.gmit.sw.Block;

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
	
	/* When creating a new block all you pass is the transaction data because the blockchain creates and knows the rest */
	@PostMapping("/add")
	public ResponseEntity<Block> addBlock(@RequestBody String s) {
		return new ResponseEntity<>(bs.addBlock(s), HttpStatus.CREATED);
	}
	
//	@PostMapping("/addTransaction")
//	public ResponseEntity<String> addTransaction(@RequestBody String s) {
//		return new ResponseEntity<>("", HttpStatus.CREATED);
//	}
	
	@GetMapping("/createWallet")
	public ResponseEntity<String[]> addWallet() {
		//bs.genrateWallet();
		return new ResponseEntity<>(bs.genrateWallet(), HttpStatus.OK);
	}

	@PostMapping("/getWallet")
	public ResponseEntity<String[]> getWallet(@RequestBody String pk) {
		System.out.println(pk);
		return new ResponseEntity<>(bs.getWallet(pk), HttpStatus.OK);
	}
	
	@PostMapping("/getBalance")
	public ResponseEntity<Float> getBalance(@RequestBody String pk) {
		return new ResponseEntity<>(bs.getBalance(pk), HttpStatus.OK);
	}
	
	@GetMapping("/transactionList")
	public ResponseEntity<ArrayList<TransactionManager>> getTransactionsToBeMined() {
		//bs.genrateWallet();
		return new ResponseEntity<>(bs.checkCurrentBlockTransactions(), HttpStatus.OK);
	}
	
	@PostMapping("/mineBlock")
	public ResponseEntity<String> mineBlock(@RequestBody String pk) {
		return new ResponseEntity<>(bs.mineBlock(pk), HttpStatus.OK);
	}
}
