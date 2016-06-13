package com.puxtech.weipan.util;

import java.util.Random;
import java.util.UUID;

/**
 * 算法工具类
 * @author fanshuo
 */
public class Algorithm {
	// 生成UUID
	public static byte[] GenerateGUID() {
		UUID uuid = UUID.randomUUID();
		byte[] least = BytesConverter.getBytes(uuid.getLeastSignificantBits());
		byte[] most = BytesConverter.getBytes(uuid.getMostSignificantBits());

		byte[] bytes = new byte[16];
		for (int i = 0; i < 8; i++) {
			bytes[i] = least[i];
			bytes[i + 8] = most[i];
		}
		return bytes;
	}

	// 生成随机数：包括字母和数字的，length代表长度
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	/**
	 * 随机生成指定长度的字符串
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
					+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	/**
	 * 将byte数组（sessionId）转换为String
	 */
	public static String getStringFromBytes(byte[] bytes) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret.append(hex);
		}
		return ret.toString();
	}
}
