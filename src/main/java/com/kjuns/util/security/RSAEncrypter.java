package com.kjuns.util.security; 

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;

import org.apache.commons.codec.binary.Base64;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-09-05
 * @file RSAEncrypter.java
 * @package com.kjuns.util.security
 * @project kjuns
 * @version 2.0
 */
public class RSAEncrypter {
	
	private static final String ENCODEING = "UTF-8";
	private static final String ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	
	
	private static final String PRIVATE_KEY_STR = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAxvL/FQTBTHtuRRvWBktttNjoE/4YiMQwrzbgm3cZuYiEJF0aSWpxSmJWdPRhPDRcbYTrP9W7b7hWEJ7jJfP1uQIDAQABAkBFJMTkkC+CHk/XsvHyZBqvxMb7087BEbZ93su3HHCTWzHvK2x8vawLLB3N2B5wQfGNqzMaQguK+XkZDQxZnEiBAiEA/U8tmY37PVPfSWLQyzec9aeoaNpm5OTH/UNUexnaCdECIQDJD/9SyJrtPC7m4TnZHOuht3pKbhjNeOkhX9+VJMO/aQIhAJ1gbPdDA/3VNxuz/f7T3XuuH26NinHZRfsusrUMma+RAiA2wZyPNwK6SQGc7wmKD048pHMxgfpPOvaCmFGTlIeawQIgMP7fq6HFLr2ItaLQ9XUnDqP7se3HBahvAbNw/pgK2xA=";
	private static final byte[] PRIVATE_KEY = Base64.decodeBase64(PRIVATE_KEY_STR);
			
	private static final int KEY_SIZE = 512;//秘钥长度 （64的整数倍，512-65536位之间，默认为1024）
	
	private static final PKCS8EncodedKeySpec pkcs8EncodeKeySpec = new PKCS8EncodedKeySpec(PRIVATE_KEY);//
	
	private static KeyFactory priKeyFac = null;
	private static KeyFactory pubKeyFac = null;
	
	static {
		try {
			priKeyFac = KeyFactory.getInstance(ALGORITHM);
			pubKeyFac = KeyFactory.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 对字符串签名
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static byte[] makeSign(String content) throws Exception{
		byte[] data = content.getBytes(ENCODEING);
		PrivateKey priKey = priKeyFac.generatePrivate(pkcs8EncodeKeySpec);//获取私钥对象
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);//获取Signature对象
		signature.initSign(priKey);
		signature.update(data);
		return signature.sign();
	}
	
	/**
	 * 验证签名
	 * @param data
	 * @param publicKey
	 * @param sign （length必须大于64）
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(String content, byte[] sign, byte[] pubKey)throws Exception{
		X509EncodedKeySpec x509EncodeKeySpec = new X509EncodedKeySpec(pubKey);
		PublicKey pbKey = pubKeyFac.generatePublic(x509EncodeKeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pbKey);
		signature.update(content.getBytes(ENCODEING));
		return signature.verify(sign);
	}
	
	/**
	 * 生成秘钥对
	 * @throws NoSuchAlgorithmException
	 */
	@SuppressWarnings("unused")
	private static void generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);//获得秘钥对生成器
		kpg.initialize(KEY_SIZE);
		KeyPair keyPair = kpg.generateKeyPair();
		RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
		
		System.out.println("公钥："+Base64.encodeBase64String(pubKey.getEncoded()));
		System.out.println("私钥："+Base64.encodeBase64String(priKey.getEncoded()));
		System.out.println("时间："+LocalDateTime.now());
	}
	
	public static void main(String[] args) throws Exception {
		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMby/xUEwUx7bkUb1gZLbbTY6BP+GIjEMK824Jt3GbmIhCRdGklqcUpiVnT0YTw0XG2E6z/Vu2+4VhCe4yXz9bkCAwEAAQ==";
		String str = "sf格格热热热热热二二二二热热热";
		byte[] sign = makeSign(str);//签名
		byte[] pbKey = Base64.decodeBase64(publicKey);
		boolean result = verifySign(str, sign, pbKey);//验签
		System.out.println(result);
	}
}
 