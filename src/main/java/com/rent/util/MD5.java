package com.rent.util;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class MD5 {

	public static String getMD5(String s) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(s.getBytes("utf-8"));
	        return toHex(bytes);
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	public static String getMD5Base64(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s.getBytes("utf-8"));
			BASE64Encoder be = new BASE64Encoder();
			return be.encode(bytes);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getFileMD5(File file) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			FileInputStream inputStream = new FileInputStream(file);
			int length = 0;
			byte[] buffer = new byte[1024];
			while ((length = inputStream.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			byte[] bytes = md.digest();
			return toHex(bytes);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getFileMD5Base64(File file) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			FileInputStream inputStream = new FileInputStream(file);
			int length = 0;
			byte[] buffer = new byte[1024];
			while ((length = inputStream.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			byte[] bytes = md.digest();

			BASE64Encoder be = new BASE64Encoder();
			return be.encode(bytes);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	private static String toHex(byte[] bytes) {

	    final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
	    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
	    return ret.toString();
	}
}
