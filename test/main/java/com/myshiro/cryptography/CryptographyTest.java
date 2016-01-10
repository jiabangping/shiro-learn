package com.myshiro.cryptography;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class CryptographyTest {
	@Test
	public void test1() {
		String str = "Hello";
		String base64Encoded = Base64.encodeToString(str.getBytes());

		System.out.println(Base64.decodeToString(base64Encoded));
	}
	
	@Test
	public void test2() {
		String str = "hello";
		String salt = "123";//��ֵ
		String md5 = new Md5Hash(str, salt).toBase64();
		System.out.println(md5);		//hvy0wFUepI7effXtlibu5w==
	}
	
	@Test
	public void testEncode() {
		//������  ����Ҫ���룬toString ,toHex(16����)
		//base64 ���Ǽ���ָ���� һ�ֱ��뷽ʽ��ת��Ϊascii�룬����Ҳ���Զ����Ľ��б��룬������������ͨ
		//����ָMD5 ��sha  sha1 sha256 ..
		/**
		 * .toBase64()
		 * .toHex() ָ���Ǳ����ʽ
		 * Ĭ���õ��� toHex() 16���Ʊ����ʽ
		 * ��ͬ�����룬ÿһ�ζ������м��� �õ��Ľ����һ���ģ����ƽ�����˿���
		 * 
		 * ����һ�������Ҫ���Լ��Ĺ��򣬼���salt��ֵ
		 */
		System.out.println(new Md5Hash("123"));
		System.out.println(new Md5Hash("���").toBase64());
		System.out.println(new Md5Hash("123").toHex());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
