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
	private HashMap<String, PublicKey> publicKeyMap = new HashMap<String, PublicKey>();
	private HashMap<String, Wallet> walletByPrivate = new HashMap<String, Wallet>();
	
	@Autowired
	public BlockchainService(BlockchainRepo br) {
		super();
		this.br = br;
	}
	
	public ArrayList<BlockManager> getAllBlocks() {
		ArrayList<BlockManager> tempBm = new ArrayList<BlockManager>();
		
		for(Block b : bc.blockchain)
		{
			ArrayList<TransactionManager> tempTm = new ArrayList<TransactionManager>();
			for(Transaction t : b.transactions)
			{
				TransactionManager tm = new TransactionManager(t.transactionId, BlockchainCryptography.getStringFromKey(t.sender), BlockchainCryptography.getStringFromKey(t.reciepient), t.amount);
				tempTm.add(tm);
			}
			BlockManager bm = new BlockManager(b.getIndex(), b.getTimestamp(), b.getPreviousHash(), 0, b.merkleRoot, b.getHash(), tempTm);
			tempBm.add(bm);
		}
		return tempBm;
	}
	
	public String[] genrateWallet() {
		Wallet generatedWallet = new Wallet();
		bc.wallets.add(generatedWallet);
		
		walletByPrivate.put(BlockchainCryptography.getStringFromKey(generatedWallet.privateKey), generatedWallet);
		publicKeyMap.put(BlockchainCryptography.getStringFromKey(generatedWallet.publicKey), generatedWallet.publicKey);
		
		String[] keys = {BlockchainCryptography.getStringFromKey(generatedWallet.privateKey), BlockchainCryptography.getStringFromKey(generatedWallet.publicKey), String.valueOf(walletByPrivate.get(BlockchainCryptography.getStringFromKey(generatedWallet.privateKey)).getBalance())};
		
		return keys;
	}
	
	public String[] getWallet(String pk) {
		walletByPrivate.get(pk);
		String[] walletInformation = {pk, BlockchainCryptography.getStringFromKey(walletByPrivate.get(pk).publicKey), String.valueOf(walletByPrivate.get(pk).getBalance())};
		
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
//		BlockManager bm = new BlockManager(bc.currentBlock.getIndex(), bc.currentBlock.getTimestamp(), bc.currentBlock.getPreviousHash(), 0, bc.currentBlock.merkleRoot, bc.currentBlock.getHash(), checkCurrentBlockTransactions());
//		br.save(bm);
		bc.addBlock(bc.currentBlock, publicKeyMap.get(pk));
		
		if(tempSize < bc.blockchain.size()) {
			return "Block Mined";
		}
		else{
			return "Block Failed To Mine";
		}
	}
	
	public String createTransaction(String senderPrivateKey, String receiverPublicKey, float amount) {
		Boolean transactionStatus = bc.currentBlock.addTransaction(walletByPrivate.get(senderPrivateKey).sendFunds(publicKeyMap.get(receiverPublicKey), amount));
		if(transactionStatus) {
			return "Transaction Completed and Successfully added to Block.";
		}
		else {
			return "Transaction Failed";
		}
		
	}


	
}
