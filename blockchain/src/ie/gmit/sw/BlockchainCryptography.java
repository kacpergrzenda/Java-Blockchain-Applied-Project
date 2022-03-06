package ie.gmit.sw;

import java.security.Key;
import java.security.MessageDigest;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

import ie.gmit.sw.transaction.Transaction;

public class BlockchainCryptography {

	public static String convertToHash(String convertToHash) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(convertToHash.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < hash.length; i++) {
				final String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) 
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}

	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	// Apply signature using private key
	public static byte[] applySignature(PrivateKey privateKey, String input) {
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}

	// Verify Fignature using public key
	public static boolean verifySignature(PublicKey publicKey, String data, byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	//Tacks in array of transactions and returns a merkle root.
	public static String getMerkleRoot(ArrayList<Transaction> transactions) {
			int count = transactions.size();
			ArrayList<String> previousTreeLayer = new ArrayList<String>();
			for(Transaction transaction : transactions) {
				previousTreeLayer.add(transaction.transactionId);
			}
			ArrayList<String> treeLayer = previousTreeLayer;
			while(count > 1) {
				treeLayer = new ArrayList<String>();
				for(int i=1; i < previousTreeLayer.size(); i++) {
					treeLayer.add(convertToHash(previousTreeLayer.get(i-1) + previousTreeLayer.get(i)));
				}
				count = treeLayer.size();
				previousTreeLayer = treeLayer;
			}
			String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
			return merkleRoot;
		}
}
