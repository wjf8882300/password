package com.tongu.search.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtil {
	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";// 默认的加密算法
/*	private static final String DEFAULT_IV = Base64.encodeBase64String("1991022819870221".getBytes());
	private static final String DEFAULT_PASSWORD = Base64.encodeBase64String("1987022119910228".getBytes());
*/
	/**
	 * AES 加密操作
	 *
	 * @param content
	 *            待加密内容
	 * @param password
	 *            加密密码
	 * @return 返回Base64转码后的加密数据
	 */
	public static String encrypt(String content, String password, String iv) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

			byte[] byteContent = content.getBytes("UTF-8");

			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password), getIv(iv));// 初始化为加密模式的密码器

			byte[] result = cipher.doFinal(byteContent);// 加密

			return Base64.encodeBase64String(result);// 通过Base64转码返回
		} catch (Exception ex) {
		}

		return null;
	}

	/**
	 * AES 解密操作
	 *
	 * @param content
	 * @param password
	 * @return
	 */
	public static String decrypt(String content, String password, String iv) {

		try {
			// 实例化
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

			// 使用密钥初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password), getIv(iv));

			// 执行操作
			byte[] result = cipher.doFinal(Base64.decodeBase64(content));

			return new String(result, "UTF-8");
		} catch (Exception ex) {
		}

		return null;
	}

	/**
	 * 生成加密秘钥
	 *
	 * @return
	 */
	private static SecretKeySpec getSecretKey(final String password) {
		// 返回生成指定算法密钥生成器的 KeyGenerator 对象
		KeyGenerator kg = null;

		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(Base64.decodeBase64(password));
			// AES 要求密钥长度为 128
			kg.init(128, secureRandom);

			// 生成一个密钥
			SecretKey secretKey = kg.generateKey();

			return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
		} catch (NoSuchAlgorithmException ex) {
		}

		return null;
	}

	/**
	 * 生成初始量
	 *
	 * @param iv
	 * @return
	 */
	private static IvParameterSpec getIv(final String iv) {
		return new IvParameterSpec(Base64.decodeBase64(iv));
	}
}
