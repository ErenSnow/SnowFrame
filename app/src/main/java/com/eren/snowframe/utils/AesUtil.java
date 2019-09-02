package com.eren.snowframe.utils;

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by apple_half on 15-1-20.
 */
public class AesUtil {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * AES/CBC/PKCS7Padding 分别对应
     * 加密||解密算法、工作模式、填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";


//    private static byte[] getKey() throws Exception {
//        return MainActivity.Encryption_SecretKey.getBytes(Charset.forName("UTF-8"));
//    }

    /**
     * 加密数据，返回 String 对象
     */
    public static String encrypt2(String toEncrypt, String key, String iv) throws Exception {
        byte[] ivAndEncryptedMessage = encrypt1(toEncrypt, key, iv);
        String strResult = new String(Base64.encode(ivAndEncryptedMessage, 0), "UTF-8");
        return strResult;
    }

    /**
     * 加密数据, 返回字节数组
     */
    public static byte[] encrypt1(String toEncrypt, String key, String iv) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);

        // libs 中 bcprov 的支持, bouncycastle 支持 64 位密钥
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 获取位移，并初始化
        final byte[] ivData = iv.getBytes();
        IvParameterSpec iv1 = new IvParameterSpec(ivData);

        // 用 iv 初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv1);

        final byte[] encryptedMessage = cipher.doFinal(toEncrypt.getBytes(Charset.forName("UTF-8")));

        final byte[] ivAndEncryptedMessage = new byte[ivData.length + encryptedMessage.length];
        System.arraycopy(ivData, 0, ivAndEncryptedMessage, 0, ivData.length);
        System.arraycopy(encryptedMessage, 0, ivAndEncryptedMessage, ivData.length, encryptedMessage.length);

        return ivAndEncryptedMessage;
    }

    /**
     * 解密
     *
     * @param encrypted1 解密前的字节数组
     * @return 解密后的字符串
     */
    public static String decrypt(byte[] encrypted1, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            //解密后的字节数组
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}