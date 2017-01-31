package com.coe.wms.common.utils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * RSA加密，公私钥、加密串等都是16进制编码
 * 
 * @author mamingli
 */
public class RSA {

	private static final Logger logger = Logger.getLogger(RSA.class);

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 得到私钥对象
	 * 
	 * @param key
	 *            密钥字符串（经过16进制编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		try {
			byte[] keyBytes = EnCodeUtil.hexStrToBytes(key.trim());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			String info = "getPrivateKey failed: " + key + " | " + e.getMessage();
			logger.error(info, e);
			throw new Exception(info, e);
		}
	}

	/**
	 * 得到公钥对象
	 * 
	 * @param key
	 *            密钥字符串（经过16进制编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		try {
			byte[] keyBytes = EnCodeUtil.hexStrToBytes(key.trim());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			String info = "getPublicKey failed: " + key + " | " + e.getMessage();
			logger.error(info, e);
			throw new Exception(info, e);
		}
	}

	/**
	 * 本方法使用SHA1withRSA签名算法产生签名
	 * 
	 * @param privateKey
	 *            privateKey 签名时使用的私钥(16进制编码)
	 * @param src
	 *            src 签名的原字符串
	 * @return String 签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。
	 * @throws PayException
	 */
	public static String sign(PrivateKey privateKey, String src, String encode) throws Exception {
		try {
			Signature sigEng = Signature.getInstance(SIGN_ALGORITHMS);
			sigEng.initSign(privateKey);
			sigEng.update(src.getBytes(encode));
			byte[] signature = sigEng.sign();
			return EnCodeUtil.bytesToHexStr(signature);
		} catch (Exception e) {
			String info = "sign failed: " + src + " | " + e.getMessage();
			logger.error(info, e);
			throw new Exception(info, e);
		}
	}

	/**
	 * 本方法使用SHA1withRSA签名算法验证签名
	 * 
	 * @param publicKey
	 *            pubKey 验证签名时使用的公钥(16进制编码)
	 * @param sign
	 *            sign 签名结果(16进制编码)
	 * @param src
	 *            src 签名的原字符串
	 * @throws PayException
	 *             验证失败时抛出异常
	 */
	public static void verify(PublicKey publicKey, String sign, String src, String encode) throws Exception {
		try {
			if (StringUtils.isBlank(sign) || StringUtils.isBlank(src)) {
				throw new Exception("sign or src isBlank");
			}
			Signature sigEng = Signature.getInstance("SHA1withRSA");
			sigEng.initVerify(publicKey);
			sigEng.update(src.getBytes(encode));
			byte[] sign1 = EnCodeUtil.hexStrToBytes(sign);
			if (!sigEng.verify(sign1)) {
				throw new Exception("验证签名失败");
			}
		} catch (Exception e) {
			String info = "verify failed: " + sign + " | " + src + " | " + e.getMessage();
			logger.error(info, e);
			throw new Exception(info, e);
		}
	}

	/**
	 * 本方法用于产生1024位RSA公私钥对。
	 * 
	 * @return 私钥、公钥
	 */
	private static String[] genRSAKeyPair() {
		KeyPairGenerator rsaKeyGen = null;
		KeyPair rsaKeyPair = null;
		try {
			logger.error("Generating a pair of RSA key ... ");
			rsaKeyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
			random.setSeed(("" + System.currentTimeMillis() * Math.random() * Math.random()).getBytes(Charset.forName("UTF-8")));
			rsaKeyGen.initialize(1024, random);
			rsaKeyPair = rsaKeyGen.genKeyPair();
			PublicKey rsaPublic = rsaKeyPair.getPublic();
			PrivateKey rsaPrivate = rsaKeyPair.getPrivate();

			String privateAndPublic[] = new String[2];
			privateAndPublic[0] = EnCodeUtil.bytesToHexStr(rsaPrivate.getEncoded());
			privateAndPublic[1] = EnCodeUtil.bytesToHexStr(rsaPublic.getEncoded());
			logger.error("私钥:" + privateAndPublic[0]);
			logger.error("公钥:" + privateAndPublic[1]);
			logger.error("1024-bit RSA key GENERATED.");

			return privateAndPublic;
		} catch (Exception e) {
			logger.error("genRSAKeyPair error：" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 私钥解密
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            密文
	 * @param encode
	 *            编码方式
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(PrivateKey privateKey, String data, String encode) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] encryptedData = Base64.decodeBase64(data);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData, encode);
	}

	// public static void main(String[] args) {
	// genRSAKeyPair();
	// }
}
