package com.puxtech.weipan.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MD5 {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取字符串的md5
	 * 
	 * @param str 待处理的字符串
	 * @return 字符串md5的结果
	 */
	public static byte[] getMD5(String str) {
		byte[] b = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			b = md.digest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public static String getMD54String(String str) {
		byte[] b = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			b = md.digest();
			return bufferToHex(b, 0, b.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	public static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static byte[] HexTobuffer(String str) {
		str = str.toUpperCase();
		int length = str.length() / 2;
		char[] hexChars = str.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	//md5加密
	public static byte[] Md5(byte[] plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(plainText);
			byte b[] = md.digest();
			return b;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return plainText;
	}

	/**
	 * 将字符串转成MD5值
	 *
	 * @param string
	 * @return
	 */
	public static String stringToMD5(String string) {
		byte[] hash;

		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString();
	}

	public static String Md5String(byte[] b){
		int i;
		StringBuffer buf = new StringBuffer();
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset] & 0xff;
			if(i<16){
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	/**
	 * AES加密
	 * @param encrypteddata
	 * @param key
	 * @return
	 */
	public static byte[] encrypt(byte[] encrypteddata, byte[] key) {

		try {
			Cipher cipher;
			SecretKeySpec mykey = new SecretKeySpec(key, "AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, mykey);// 初始化
			byte[] result = cipher.doFinal(encrypteddata);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * AES解密
	 * @param encrypteddata
	 * @param key
	 * @return
	 */
	public static byte[] decrypt(byte[] encrypteddata, byte[] key) {

		try {
			Cipher cipher;
			SecretKeySpec mykey = new SecretKeySpec(key, "AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, mykey);// 初始化
			byte[] result = cipher.doFinal(encrypteddata);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
