package cc.stevenyin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class MD5Utils {

	/**
	 * @throws NoSuchAlgorithmException 
	 * @Description: 对字符串进行md5加密 
	 */
	public static String getMD5Str(String strValue) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
	}
}
