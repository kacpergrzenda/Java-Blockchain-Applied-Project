package ie.gmit.sw;

import java.security.MessageDigest;

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

}
