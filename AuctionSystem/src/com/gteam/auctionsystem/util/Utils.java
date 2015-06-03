package com.gteam.auctionsystem.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	private static String convertedToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfOfByte = (data[i] >>> 4) & 0x0F;
			int twoHalfBytes = 0;
			do {
				if ((0 <= halfOfByte) && (halfOfByte <= 9)) {
					buf.append((char) ('0' + halfOfByte));
				}
				else {
					buf.append((char) ('a' + (halfOfByte - 10)));
				}
				halfOfByte = data[i] & 0x0F;
			}
			while (twoHalfBytes++ < 1);
		}
		return buf.toString();
	}

	public static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte[] md5 = new byte[64];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		md5 = md.digest();
		return convertedToHex(md5);
	}
	
	public static String getFormattedDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		return dateFormat.format(date);
	}
}