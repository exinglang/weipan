package com.puxtech.weipan.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * aes加密
 * 
 * @author fanshuo
 * @version 1.0
 */
public class AES {
	/**
	 * 加密
	 */
	public static byte[] encrypt(byte[] byteContent, byte[] password)
			throws Exception {
		SecretKeySpec key = new SecretKeySpec(password, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(byteContent);
		return result; // 加密
	}

	/**
	 * 解密
	 */
	public static byte[] decrypt(byte[] content, byte[] password)
			throws Exception {
		SecretKeySpec key = new SecretKeySpec(password, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(content);
		return result; // 加密
	}
	/**
	 * 先aes加密，然后再做base64加密
	 */
	public static String encrypt(String text, String key) throws Exception {
		new Base64();
		
		return Base64.encode(encrypt(text.getBytes(), key.getBytes()));
	}
	
	/**
	 * 先进行base64解密，然后aes解密
	 */
	public static String decrypt(String text, String key) throws Exception {
		return new String(decrypt(Base64.decode(text), key.getBytes()));
	}
	
}