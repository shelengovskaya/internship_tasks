package itransition2;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hmac {
	
	static public byte[] generate_key() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[16];
		random.nextBytes(bytes);
		return bytes;
	}
	
	static public void printHmacSha256(byte[] key, String message) {
		try {
			byte[] hmacSha256 = calcHmacSha256(key.toString().getBytes("UTF-8"), message.getBytes("UTF-8"));
			System.out.println(String.format("HMAC: %032x", new BigInteger(1, hmacSha256))); 
		} catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
	}
	
	static public byte[] calcHmacSha256(byte[] secretKey, byte[] message) {
	    byte[] hmacSha256 = null;
	    try {
	      Mac mac = Mac.getInstance("HmacSHA256");
	      SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
	      mac.init(secretKeySpec);
	      hmacSha256 = mac.doFinal(message);
	    } catch (Exception e) {
	      throw new RuntimeException("Failed to calculate hmac-sha256", e);
	    }
	    return hmacSha256;
	  }
}
