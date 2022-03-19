package ie.gmit.blockchainmanager.service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.gmit.blockchainmanager.model.BlockManager;
import ie.gmit.blockchainmanager.model.TransactionManager;
import ie.gmit.blockchainmanager.repo.BlockchainRepo;
import ie.gmit.sw.Block;
import ie.gmit.sw.Blockchain;
import ie.gmit.sw.BlockchainCryptography;
import ie.gmit.sw.Wallet;
import ie.gmit.sw.transaction.Transaction;

@Service
public class BlockchainService {
	private final BlockchainRepo br;
	private final Blockchain bc = new Blockchain(); /* Create a instance of the Blockchain from Blockchain class */
	private HashMap<String, PrivateKey> privateKeyMap = new HashMap<String, PrivateKey>();
	private HashMap<String, PublicKey> publicKeyMap = new HashMap<String, PublicKey>();
	private HashMap<String, String> publicKeyByPrivate = new HashMap<String, String>();
	private HashMap<String, Wallet> walletByPrivate = new HashMap<String, Wallet>();
	
	@Autowired
	public BlockchainService(BlockchainRepo br) {
		super();
		this.br = br;
	}
	
	
	public Block addBlock(String publicKey) {	
		Block b = bc.addBlock(bc.currentBlock ,publicKeyMap.get(publicKey));
		//System.out.println("Service:" + bc.blockchain);
		//BlockManager bm = new BlockManager( b.getTimestamp() , b.getTransactionData(), b.getPreviousHash(), b.getHash(), 0); // creates a copy of the existing block 
		//br.save(bm); // saves a block into the mysql database
		System.out.println(bc.blockchain);
		return b;
	}
	
	public ArrayList<Block> getAllBlocks() {
		return bc.blockchain;
	}
	
	public String[] genrateWallet() {
		Wallet generatedWallet = new Wallet();
		bc.wallets.add(generatedWallet);
		walletByPrivate.put(BlockchainCryptography.getStringFromKey(generatedWallet.privateKey), generatedWallet);
		privateKeyMap.put(BlockchainCryptography.getStringFromKey(generatedWallet.privateKey), generatedWallet.privateKey);
		publicKeyMap.put(BlockchainCryptography.getStringFromKey(generatedWallet.publicKey), generatedWallet.publicKey);
		publicKeyByPrivate.put(BlockchainCryptography.getStringFromKey(generatedWallet.privateKey), BlockchainCryptography.getStringFromKey(generatedWallet.publicKey));
		/* Test */
		System.out.println(privateKeyMap.get(BlockchainCryptography.getStringFromKey(generatedWallet.privateKey)));
		System.out.println(publicKeyMap.get(BlockchainCryptography.getStringFromKey(generatedWallet.publicKey)));
		String[] keys = {BlockchainCryptography.getStringFromKey(generatedWallet.privateKey), BlockchainCryptography.getStringFromKey(generatedWallet.publicKey), String.valueOf(walletByPrivate.get(BlockchainCryptography.getStringFromKey(generatedWallet.privateKey)).getBalance())};
		System.out.println("private: " + BlockchainCryptography.getStringFromKey(generatedWallet.privateKey));
		System.out.println("public: " + BlockchainCryptography.getStringFromKey(generatedWallet.publicKey));
		System.out.println(publicKeyMap);
		return keys;
	}
	
	public String[] getWallet(String pk) {
		walletByPrivate.get(pk);
		String[] walletInformation = {pk,publicKeyByPrivate.get(pk), String.valueOf(walletByPrivate.get(pk).getBalance())};
		
		System.out.println(walletByPrivate.get(pk).getBalance());
		return walletInformation;
	}
	
	public float getBalance(String pk) {
		return walletByPrivate.get(pk).getBalance();
	}
	
	public ArrayList<TransactionManager> checkCurrentBlockTransactions() {
		ArrayList<TransactionManager> allTransactions = new ArrayList<TransactionManager>();
		if(bc.blockchain.size() <= 0) {
			bc.createGenesisBlock();
		}

		for (Transaction t : bc.currentBlock.transactions) {
			TransactionManager tm = new TransactionManager(t.transactionId, BlockchainCryptography.getStringFromKey(t.sender), BlockchainCryptography.getStringFromKey(t.reciepient), t.amount);
			allTransactions.add(tm);
		}

		return allTransactions;
	}
	
	public String mineBlock(String pk) {
		int tempSize = bc.blockchain.size();
		bc.addBlock(bc.currentBlock, publicKeyMap.get(pk));
		if(tempSize < bc.blockchain.size()) {
			
			String s = "Block Mined";
			return s;
		}
		else{
			String s = "Block Failed To Mine";
			return s;
		}
	}
	
	


	
}
