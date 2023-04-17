import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES encryption. Default to use 256 bits key. Need install JCE package for more bits key.
 *
 */

public class PasswordAESEncryptionUtil {

	//do not modify this keySeedStr. 
	private static String keySeedStr = "Z8LSq0wWwB5v+6YJzurcP463H3F12iZh74fDj4S74oUH4EONkiKb2FmiWUbtFh97GG/";
	private static int pswdIterations = 65536;
	private static int keySize256 = 256;   // 256 bits key
	private static int keySize = keySize256;
	private static int randomKeySize = 85;
	
	/**
	 * to encrypt
	 * @param plainText
	 * @param tenantKey
	 * @return
	 */
	public static String encryptWithKey(String plainText, String tenantKey) throws Exception{
		
		if(tenantKey != null && tenantKey.contains(":") ) // tenantKey is encrypted with the default key
			tenantKey = decrypt(tenantKey);
		
		byte[] saltBytes = generateSalt();
		SecretKeySpec secret = getAESKeySpec(tenantKey,saltBytes);
		//encrypt the plain text
		Cipher cipher = getCipher();
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		byte[]ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		
		return Base64.getEncoder().encodeToString(saltBytes) + ":" + 
		Base64.getEncoder().encodeToString(ivBytes) + ":" +
		Base64.getEncoder().encodeToString(encryptedTextBytes);
	}
		
	private static SecretKeySpec getAESKeySpec(String tenantKey, byte[] saltBytes) throws Exception {	
		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(
			tenantKey.toCharArray(), 
			saltBytes, 
			pswdIterations, 
			keySize);
		SecretKey secretKey = factory.generateSecret(spec);
		return new SecretKeySpec(secretKey.getEncoded(), "AES");
	}
	
	/**
	 * to decrypt
	 * @param encryptedText
	 * @param tenantKey
	 * @return
	 */
	
	public static String decryptWithKey(String encryptedText, String tenantKey) throws Exception {
		if(tenantKey != null && tenantKey.contains(":") ) // tenantKey is encrypted with the default key.
			tenantKey = decrypt(tenantKey);
		
		String[] encryptTexts = encryptedText.split(":");
		
		byte[] saltBytes = Base64.getDecoder().decode(encryptTexts[0]);
		byte[] ivBytes   = Base64.getDecoder().decode(encryptTexts[1]);
		byte[] encryptedTextBytes = Base64.getDecoder().decode(encryptTexts[2]);
		
		SecretKeySpec secret=getAESKeySpec(tenantKey, saltBytes);
		// Decrypt the message
		Cipher cipher = getCipher();
		try{
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
		} catch(Exception e) {
			throw e;
		}
		
		byte[] decryptedTextBytes = null;
		try {
			decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		} catch (IllegalBlockSizeException e) {
			throw e;
		} catch (BadPaddingException e) {
			throw e;
		}
		return new String(decryptedTextBytes);
	}
	
	/**
	 * defaut encryption 
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String plainText) throws Exception {
		byte[] saltBytes = generateSalt();
		SecretKeySpec secret = getAESKeySpec(keySeedStr, saltBytes);
		//encrypt the message
		Cipher cipher = getCipher();
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		byte[]ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		
		return Base64.getEncoder().encodeToString(saltBytes) + ":" + Base64.getEncoder().encodeToString(ivBytes) + ":" +
		Base64.getEncoder().encodeToString(encryptedTextBytes);
	}

	/**
	 * default encryption
	 * @param encryptedText
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptedText) throws Exception {
		String[] encryptTexts = encryptedText.split(":");
		
		byte[] saltBytes = Base64.getDecoder().decode(encryptTexts[0]);
		byte[] ivBytes   = Base64.getDecoder().decode(encryptTexts[1]);
		byte[] encryptedTextBytes = Base64.getDecoder().decode(encryptTexts[2]);
		
		SecretKeySpec secret = getAESKeySpec(keySeedStr, saltBytes);
		// Decrypt the message
		Cipher cipher = getCipher();
		try{
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
		} catch(Exception e) {
			throw e;
		}
		
		byte[] decryptedTextBytes = null;
		try {
			decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		} catch (IllegalBlockSizeException e) {
			throw e;
		} catch (BadPaddingException e) {
			throw e;
		}
		return new String(decryptedTextBytes);
	}

	/**
	 * to generate a random salt.
	 * @return
	 */
	private static byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[16];
		random.nextBytes(bytes);
		return bytes;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String generateEncrpytedRandomKeyStr() throws Exception{
		String s = RandomKeyGenerator.randomString(keySize256);
		return encrypt(s); 
	}
	
	public static String getKeySeedStr() {
		return keySeedStr;
	}

	public static void setKeySeedStr(String keySeedStr) {
		PasswordAESEncryptionUtil.keySeedStr = keySeedStr;
	}
	
	/**
	 * Cipher object has set with Cipher Block Chaining and PKC5Padding hence not making any change in encryption key and @Suppressed Warning
	 */
	@SuppressWarnings("squid:S4432") 
	private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException{
		@SuppressWarnings("squid:S1192")
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		return cipher;
	}
	
}
