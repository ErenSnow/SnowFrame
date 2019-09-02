package com.eren.snowframe.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static byte[] encrypt(String content, String password, String iv) throws Exception {
        // 创建AES秘钥
        SecretKeySpec key = new SecretKeySpec(password.getBytes(),"AES/CBC/PKCS7PADDING");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
        // 初始化加密器
        cipher.init(Cipher.ENCRYPT_MODE, key,ivspec);
        // 加密
        return cipher.doFinal(content.getBytes("UTF-8"));
    }

    public static byte[] decrypt(byte[] content, String password) throws Exception {
        // 创建AES秘钥
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES/CBC/PKCS7PADDING");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
        // 初始化解密器
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 解密
        return cipher.doFinal(content);
    }
}
