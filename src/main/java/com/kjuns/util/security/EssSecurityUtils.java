package com.kjuns.util.security; 

import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjuns.util.CommonUtils;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-09-05
 * @file EssSecurityUtils.java
 * @package com.kjuns.util.security
 * @project kjuns
 * @version 2.0
 */
public class EssSecurityUtils {
	private static Logger logger = LoggerFactory.getLogger(EssSecurityUtils.class);
	
	private static final String ENCODEING = "UTF-8";
	private static final String ALGORITHM = "AES";
	private static final String ENCRYPT_KEY = "nb4De5IBpZBTKkCybV9HdgjmtAhjAVw2ZZnJQbvwPC91XXpCoAFGo5IJzVuT5IlemG6cGVatAZpsj6gFdYTDzA8trfKFQxFcuS4sXUG8AdWF5XWofpSy2gSvtowPimeZ";
	
	private static Cipher cipher_encrypt = null;//加密密码器
	private static Cipher cipher_decrypt = null;//解密密码器
	
	static{
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
	        secureRandom.setSeed(ENCRYPT_KEY.getBytes());
			kgen.init(128, secureRandom); 
			SecretKey secretKey = kgen.generateKey();
			byte[] secretKeyEncoded = secretKey.getEncoded();
			SecretKeySpec sks = new SecretKeySpec(secretKeyEncoded, ALGORITHM);
			cipher_encrypt = Cipher.getInstance(ALGORITHM);
			cipher_encrypt.init(Cipher.ENCRYPT_MODE, sks);
			cipher_decrypt = Cipher.getInstance(ALGORITHM);
			cipher_decrypt.init(Cipher.DECRYPT_MODE, sks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加密
	 * @param content 需要加密的内容
	 * @return
	 */
	public static String encrypt(String content) throws Exception{
			byte[] result = cipher_encrypt.doFinal(content.getBytes(ENCODEING));
			return  Base64.encodeBase64String(result);
	}
	
	/**
	 * 解密
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String content) throws Exception {
			byte[] result =  cipher_decrypt.doFinal(Base64.decodeBase64(content));
			return new String(result,ENCODEING);
	}
	
	/**
	 * 验证签名
	 * @param dataMap
	 * @param signature
	 * @param pubKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(LinkedHashMap<String,Object> dataMap, String signature, String pubKey) throws Exception{
		StringBuilder data = new StringBuilder();
		for (Map.Entry<String, Object> map : dataMap.entrySet()) {
			data.append(map.getValue());//按顺序连接
		}
		byte[] sign = Base64.decodeBase64(signature);
		if(sign.length != 64){
			logger.error("签名的 byte[]长度错误，应该大于、等于64。当前长度：{}",sign.length);
			return false;
		}
		String content = data.toString();
		byte[] pubKeyArr = Base64.decodeBase64(pubKey);
		boolean verifyResult = RSAEncrypter.verifySign(content, sign, pubKeyArr);
		if(verifyResult == false){
			logger.info("验签失败 - 待验签的内容：" + content);
			logger.info("验签失败 - 客户端签名：" + signature);
			logger.info("验签失败 - 公钥：" + pubKey);
		}
		return verifyResult;
	}
	
	/**
	 * MD5运算
	 */
	public static boolean verifySign(TreeMap<String, Object> treeMap, String signature, String key) throws Exception{
		StringBuffer sbff = new StringBuffer();
		//字典排序
		for(Map.Entry<String, Object> m: treeMap.entrySet()){
	   		sbff.append(m.getKey()).append(m.getValue());
	   	}
		//字段拼接大写 + 密钥
	    String keyStr = sbff.toString().toUpperCase() + key;
	    //MD5运算
	    String sign = CommonUtils.md5Encode32(keyStr);
		if(sign.equals(signature)){
			return true;
		}else{
			logger.error("参与签名字符：{}", keyStr);
		    logger.error("客户端签名：{}", signature);
		    logger.error("服务端签名：{}", sign);
			return false;
		}
	}

	
	private static String makeSign(String content) throws Exception{
		byte[] signature = RSAEncrypter.makeSign(content);
		return Base64.encodeBase64String(signature);
	}

	public static void main(String[] args) throws Exception {
		//System.out.println(CommonUtils.getRandomCharAndNum(128));
		
		/*String content = "方法wfwfwfjwoijfwioejiop";
		String signature = makeSign(content);
		System.out.println("sign:"+signature);
		
		System.out.println(verifySign(content, signature));*/
		
		System.out.println(makeSign("偶像计划"));
		
	}

}
 