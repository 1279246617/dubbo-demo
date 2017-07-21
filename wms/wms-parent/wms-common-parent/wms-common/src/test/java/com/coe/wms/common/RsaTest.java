package com.coe.wms.common;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.coe.wms.common.utils.RSA;

public class RsaTest {
public static void main(String[] args) throws Exception {
	
	
	/*String testStr="12345678";
	PrivateKey privateKey = RSA.getPrivateKey(testStr);
	String algorithm = privateKey.getAlgorithm();
	PublicKey publicKey = RSA.getPublicKey(testStr);
	String algorithm2 = publicKey.getAlgorithm();
	System.out.println("algorithm:"+algorithm);
	System.out.println("algorithm2:"+algorithm2);*/
	String[] genRSAKeyPairArr = RSA.genRSAKeyPair();
	
	String privateKey=genRSAKeyPairArr[0];
	String publicKey=genRSAKeyPairArr[1];
	
	PrivateKey privateKey2 = RSA.getPrivateKey(privateKey);
	PublicKey publicKey2 = RSA.getPublicKey(publicKey);
	
	String sign = RSA.sign(privateKey2, "123456", "UTF-8");
	
	System.out.println(sign);
	
	
//	String decrypt = RSA.decrypt(privateKey2, "123456", "UTF-8");
	RSA.verify(publicKey2, sign+"1", "123456", "UTF-8");
//	System.out.println(decrypt);
	
}
}
