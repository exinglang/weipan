package com.puxtech.weipan.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * byte转换工具类
 * 
 * @author fanshuo
 */
public final class BytesConverter {

	public static String getStringWithByteLength(byte[] bytes,
			Integer startIndex) {
		byte length = bytes[startIndex++];
		String resultString = new String(bytes, startIndex, length);
		return resultString;
	}

	public static String getStringWithIntLength(byte[] bytes, Integer startIndex) {
		int length = BytesConverter.getInt(bytes, startIndex);
		startIndex += 4;
		String resultString = new String(bytes, startIndex, length);
		return resultString;
	}

	public static String getStringWithoutLength(byte[] bytes,
			Integer startIndex, int length) {
		String resultString = new String(bytes, startIndex, length);
		return resultString;
	}

	public static short getShort(byte[] bytes, int startIndex) {
		return (short) getNumber(bytes, startIndex, 2);
	}

	public static int getInt(byte[] bytes, int startIndex) {
		return (int) getNumber(bytes, startIndex, 4);
	}

	public static long getLong(byte[] bytes, int startIndex) {
		return (long) getNumber(bytes, startIndex, 8);
	}

	public static float getFloat(byte[] bytes, int startIndex) {
		int intBits = getInt(bytes, startIndex);
		return Float.intBitsToFloat(intBits);
	}

	public static Double getDouble(byte[] bytes, int startIndex) {
		long longBits = getLong(bytes, startIndex);
		return Double.longBitsToDouble(longBits);
	}

	public static byte[] getBytes(byte[] bytes, int startIndex, int length) {
		byte[] values = new byte[length];
		for (int i = 0; i < length; i++) {
			values[i] = bytes[startIndex + i];
		}
		return values;
	}

	private static long getNumber(byte[] bytes, int startIndex, int count) {
		long num = 0;
		for (int i = startIndex; i < startIndex + count; i++) {
			num <<= 8;
			num |= (bytes[i] & 0xff);
		}
		return num;
	}

	public static byte[] getBytes(short value) {
		byte[] bytes = new byte[2];
		for (int i = 0; i < 2; i++) {
			bytes[i] = (byte) (value >>> (8 - i * 8));
		}
		return bytes;
	}

	public static byte[] getBytes(int value) {
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			bytes[i] = (byte) (value >>> (24 - i * 8));
		}
		return bytes;
	}

	public static byte[] getBytes(long value) {
		byte[] bytes = new byte[8];
		for (int i = 0; i < 8; i++) {
			bytes[i] = (byte) (value >>> (56 - i * 8));
		}
		return bytes;
	}

	public static byte[] getBytes(boolean value) {
		return new byte[] { value ? (byte) 1 : (byte) 0 };
	}

	public static byte[] getBytes(float value) {
		int intBits = Float.floatToIntBits(value);
		return getBytes(intBits);
	}

	public static byte[] getBytes(double data) {
		long intBits = Double.doubleToLongBits(data);
		return getBytes(intBits);
	}

	public static byte[] getBytes(String value) {
		return value.getBytes();
	}

	public static byte[] getBytes(InputStream stream) throws IOException {
		byte[] bytes = new byte[stream.available()];
		stream.read(bytes);
		return bytes;
	}

	public static byte[] getContentBytes(InputStream stream) throws IOException {
		byte[] contentLength = new byte[1];
		stream.read(contentLength);
		byte[] content = new byte[contentLength[0]];
		stream.read(content);
		return content;
	}

	public static String getKeyFromBytes(byte[] bytes) {

		String ret = "";
		for (int i = 0; i < bytes.length; i++) {

			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex;
		}
		return ret;
	}

	public static byte[] stringToByte(String in) {

		byte[] b = new byte[in.length() / 2];

		int j = 0;
		StringBuffer buf = new StringBuffer(2);
		for (int i = 0; i < in.length(); i++, j++) {
			buf.insert(0, in.charAt(i));
			buf.insert(1, in.charAt(i + 1));
			int t = Integer.parseInt(buf.toString(), 16);
			b[j] = (byte) t;
			i++;
			buf.delete(0, 2);
		}

		return b;
	}
}
