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
		String salt = "123";//盐值
		String md5 = new Md5Hash(str, salt).toBase64();
		System.out.println(md5);		//hvy0wFUepI7effXtlibu5w==
	}
	
	@Test
	public void testEncode() {
		//加完密  还需要编码，toString ,toHex(16进制)
		//base64 不是加密指的是 一种编码方式来转换为ascii码，比如也可以对中文进行编码，来在网络中流通
		//加密指MD5 和sha  sha1 sha256 ..
		/**
		 * .toBase64()
		 * .toHex() 指的是编码格式
		 * 默认用的是 toHex() 16进制编码格式
		 * 相同的密码，每一次对它进行加密 得到的结果是一样的，对破解带来了可能
		 * 
		 * 所以一般加密需要有自己的规则，加入salt盐值
		 */
		System.out.println(new Md5Hash("123"));
		System.out.println(new Md5Hash("你好").toBase64());
		System.out.println(new Md5Hash("123").toHex());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
