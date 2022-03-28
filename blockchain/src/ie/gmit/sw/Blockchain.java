package ie.gmit.sw;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.security.PublicKey;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import ie.gmit.sw.transaction.Transaction;
import ie.gmit.sw.transaction.TransactionInput;
import ie.gmit.sw.transaction.TransactionOutput;

public class Blockchain {
	
	public ArrayList<Block> blockchain = new ArrayList<Block>();
	private int difficulty = 2;
	public static ArrayList<Wallet> wallets = new ArrayList<Wallet>();
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions.
	
	public Wallet coinBase = new Wallet();;
	public Transaction genesisTransaction;
	public Block currentBlock;
	private float MINING_REWARD = 10f;
	
	/* Checks if the Blockchain is valid by running a consensus by all the blocks in the chain */
	public Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
		tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		
		
		//loop through block-chain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			String hashTarget = new String(new char[difficulty]).replace('\0', '0');
			//compare registered hash and calculated hash:
			if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal: " + currentBlock.getHash());			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			
			//check if hash is solved
			if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}
			
			//loop thru blockchains transactions:
			TransactionOutput tempOutput;
			for(int j=0; j <currentBlock.transactions.size(); j++) {
				Transaction currentTransaction = currentBlock.transactions.get(j);
				
				if(!currentTransaction.verifiySignature()) {
					System.out.println("#Signature on Transaction(" + j + ") is Invalid");
					return false; 
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("#Inputs are note equal to outputs on Transaction(" + j + ")");
					return false; 
				}
				for(TransactionInput input: currentTransaction.inputs) {	
					tempOutput = tempUTXOs.get(input.transactionOutputId);
					
					if(tempOutput == null) {
						System.out.println("#Referenced input on Transaction(" + j + ") is Missing");
						return false;
					}
					
					if(input.unspentTXOutput.value != tempOutput.value) {
						System.out.println("#Referenced input Transaction(" + j + ") value is Invalid");
						return false;
					}
					
					tempUTXOs.remove(input.transactionOutputId);
				}
				
				for(TransactionOutput output: currentTransaction.outputs) {
					tempUTXOs.put(output.id, output);
				}
				
				if( currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
					System.out.println("#Transaction(" + j + ") output reciepient is not who it should be");
					return false;
				}
				if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
					System.out.println("#Transaction(" + j + ") output 'change' is not sender.");
					return false;
				}
			}
		}
		return true;
	}

	/* Retrieve the latest Hash of the newest Block */
	private String getLatestBlockHash() {
		if(blockchain.size() <= 0) {
			return "0";
		}else{
			return blockchain.get(blockchain.size()-1).getHash();
		}
	}
	
	/* Verify and Add the newest block onto the Blockchain, Reward Miner with a reward */
	public Block addBlock(Block b,PublicKey walletPkAwarded) {
		if(blockchain.size() <= 0)
		{
			System.out.println("FIRST FIRST Block");
			b.mineBlock(this.difficulty); // MineTheBlock
			blockchain.add(this.currentBlock); // Once block is mined add the new block to block-chain
			this.currentBlock = new Block(blockchain.size(), getLatestBlockHash());
			this.currentBlock.addTransaction(coinBase.sendFunds(walletPkAwarded, MINING_REWARD));
			MINING_REWARD += 1;
			return this.currentBlock;
		}
		else
		{			
			this.currentBlock.addTransaction(coinBase.sendFunds(walletPkAwarded, MINING_REWARD));
			MINING_REWARD += 1;
			this.currentBlock.mineBlock(this.difficulty); // MineTheBlock
			blockchain.add(this.currentBlock); // Once block is mined add the new block to block-chain
			this.currentBlock = new Block(blockchain.size(), getLatestBlockHash());
			System.out.println(isChainValid());
			return this.currentBlock;
		}		
	}
	
	/* Generates and create the Gensis block */
	public void createGenesisBlock() {
		genesisTransaction = new Transaction(this.coinBase.publicKey, this.coinBase.publicKey, 100000f, null);
		genesisTransaction.generateSignature(this.coinBase.privateKey);	 //manually sign the genesis transaction	
		genesisTransaction.transactionId = "0"; //manually set the transaction id
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.amount, genesisTransaction.transactionId)); //manually add the Transactions Output
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.
		this.currentBlock = new Block(blockchain.size(), getLatestBlockHash());// Genesis Block Create
		currentBlock.addTransaction(genesisTransaction);
	}
}
