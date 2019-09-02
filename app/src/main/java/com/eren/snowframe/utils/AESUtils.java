package com.eren.snowframe.utils;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    /**
     * 加密
     *
     * @param data 加密前的字符串
     * @return 加密后的字节数组
     */
    public static byte[] aesUtil(String data, String key, String iv) {
        // 加密
        Log.d("HeaderInterceptor", "需要加密的data:" + data);
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            int blockSize = cipher.getBlockSize();

            //判断待加密的字节数组的长度，在此长度基础上扩展一个字节数组的长度为16的倍数
            byte[] dataBytes = data.getBytes("UTF-8");
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            //创建需新的待加密的字节数组，将上面的字节数组复制进来，多余的补0
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

//            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES/CBC/PKCS7PADDING");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

            //加密后的字节数组
            byte[] encrypted = cipher.doFinal(plaintext);
            return encrypted;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     *
     * @param encrypted1 解密前的字节数组
     * @return 解密后的字符串
     */
    public static String decrypt(byte[] encrypted1, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES/CBC/PKCS7PADDING");
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
//
//    /**
//     * 加密
//     *
//     * @param data 加密前的字符串
//     * @return 加密后的字节数组
//     */
//    public static String aesUtil(String data, String key, String iv) {
//        try {
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            int blockSize = cipher.getBlockSize();
//
//            //判断待加密的字节数组的长度，在此长度基础上扩展一个字节数组的长度为16的倍数
//            byte[] dataBytes = data.getBytes();
//            int plaintextLength = dataBytes.length;
//            if (plaintextLength % blockSize != 0) {
//                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
//            }
//
//            //创建需新的待加密的字节数组，将上面的字节数组复制进来，多余的补0
//            byte[] plaintext = new byte[plaintextLength];
//            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
//
//            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
//
//            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
//
//            // 加密后的字节数组
//            byte[] encrypted = cipher.doFinal(plaintext);
//
//            String originalString = new String(encrypted);
//            return originalString;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 解密
//     *
//     * @param encrypted1 解密前的字节数组
//     * @return 解密后的字符串
//     */
//    public static String decrypt(byte[] encrypted1, String key, String iv) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
//
//            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
//
//            //解密后的字节数组
//            byte[] original = cipher.doFinal(encrypted1);
//            String originalString = new String(original);
//            return originalString;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
