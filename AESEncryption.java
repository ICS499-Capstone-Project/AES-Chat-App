package com.test2.ics483.edu;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {
	private static final String AES = "AES";
	private static final String KEY_PHRASE = "This is the KEY";
	private static final String SALT = "Test Salt";

	public static SecretKey getSecretEncryptionKey() throws Exception, BadPaddingException {
		KeyGenerator generator = KeyGenerator.getInstance(AES);

		generator.init(256); // The AES key size in number of bits
		SecretKey secKey = generator.generateKey();
		return secKey;
	}

	public static byte[] encryptText(String plainText, SecretKey secKey) throws Exception, BadPaddingException {

		byte[] salt = SALT.getBytes("UTF-8");

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		PBEKeySpec spec = new PBEKeySpec(KEY_PHRASE.toCharArray(), salt, 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey key = new SecretKeySpec(tmp.getEncoded(), "AES");

		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
		return byteCipherText;
	}

	public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception, BadPaddingException {
		byte[] salt = SALT.getBytes("UTF-8");

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		PBEKeySpec spec = new PBEKeySpec(KEY_PHRASE.toCharArray(), salt, 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey key = new SecretKeySpec(tmp.getEncoded(), "AES");

		Cipher aesCipher = Cipher.getInstance(AES);
		aesCipher.init(Cipher.DECRYPT_MODE, key);
		byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
		return new String(bytePlainText);
	}
}
