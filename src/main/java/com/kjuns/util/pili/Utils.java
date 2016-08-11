package com.kjuns.util.pili;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Utils {
	private static final String USER_AGENT = "piliio-java";
	private static final String DIGEST_AUTH_PREFIX = "pili";

	public static String getUserAgent(String version) {
		String ua = USER_AGENT + "/" + version + "java/1.7";
		return ua;
	}

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	private static byte[] encodeBase64Ex(byte[] src) {
		// urlsafe version is not supported in version 1.4 or lower.
		byte[] b64 = Base64.encodeBase64(src);

		for (int i = 0; i < b64.length; i++) {
			if (b64[i] == '/') {
				b64[i] = '_';
			} else if (b64[i] == '+') {
				b64[i] = '-';
			}
		}
		return b64;
	}

	public static String toString(byte[] bs)
			throws UnsupportedEncodingException {
		return new String(bs, "utf-8");
	}

	public static String sign(String key, String value) {
		try {
			// Get an hmac_sha1 key from the raw key bytes
			byte[] keyBytes = key.getBytes(ENCODING);
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, MAC_NAME);

			// Get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(MAC_NAME);
			mac.init(signingKey);

			byte[] rawHmac = mac.doFinal(value.getBytes(ENCODING));

			// Convert raw bytes to Hex
			return toString(encodeBase64Ex(rawHmac));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unused")
	private static String byte2hex(final byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0xFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}

	public static String signRequests(String accessKey, String secretKey,
			String url, String body) throws Exception {
		URL console = new URL(url);
		String data = "";
		if (console.getPath() != null) {
			data = console.getPath();
		}
		if (console.getQuery() != null) {
			data += '?' + console.getQuery();
		}
		data += '\n';
		if (body != null && body.length() > 0) {
			data += body.toString();
		}
		return DIGEST_AUTH_PREFIX + ' ' + accessKey + ':'
				+ sign(secretKey, data);
	}

}
