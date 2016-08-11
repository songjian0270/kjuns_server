package com.kjuns.util.security;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

//import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-09-05
 * @file EncryptUtil.java
 * @package com.kjuns.util.security
 * @project kjuns
 * @version 2.0
 */
public class EncryptUtil{
	
	private static final String ALGORITHM = "HmacSHA224";
	private static final String ENCODEING = "UTF-8";
	private static final String ENCRYPT_KEY = "nvkzlY32Y3lJRweEv+9SmEyFLh64MflotJUQAQ==";
	private static final byte[] key = Base64.decode(ENCRYPT_KEY);
	private static final SecretKey secretKey = new SecretKeySpec(key,ALGORITHM);
	private static Mac mac;

	static{
		Security.addProvider(new BouncyCastleProvider());
	}
	
	/**
	 * 对字符串进行加密(MAC)
	 * @param text 明文
	 * @return 密文
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static String encode(String text) throws Exception {
		mac = (mac == null) ? Mac.getInstance(secretKey.getAlgorithm()) : mac;
		mac.init(secretKey);
		byte[] codedText = mac.doFinal(text.getBytes(ENCODEING));
		return new String(Hex.encode(codedText),ENCODEING);
	}
	
}
