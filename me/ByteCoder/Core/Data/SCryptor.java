package me.ByteCoder.Core.Data;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;

public class SCryptor {

public static String aesEncryptString(String data, String skey) {
	String s = "";
	try {
		s = encrypt(data, skey);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return s;
}

public static String aesDecryptString(String data, String skey) {
	String s = "";
	try {
		s = decrypt(data, skey);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return s;
}

public static String encrypt(String data, String skey) throws Exception {
    Key key = new SecretKeySpec(skey.getBytes(), "AES");
    Cipher c = Cipher.getInstance("AES");
    c.init(Cipher.ENCRYPT_MODE, key);
    byte[] encVal = c.doFinal(data.getBytes());
    return Base64.getEncoder().encodeToString(encVal);
}

/**
 * Decrypt a string with AES algorithm.
 *
 * @param encryptedData is a string
 * @return the decrypted string
 */
public static String decrypt(String encryptedData, String skey) throws Exception {
    Key key = new SecretKeySpec(skey.getBytes(), "AES");
    Cipher c = Cipher.getInstance("AES");
    c.init(Cipher.DECRYPT_MODE, key);
    byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
    byte[] decValue = c.doFinal(decordedValue);
    return new String(decValue);
}
}