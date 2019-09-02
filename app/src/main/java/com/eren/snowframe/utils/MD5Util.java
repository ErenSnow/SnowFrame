package com.eren.snowframe.utils;

import java.security.MessageDigest;

/**
 * @author Eren
 * <p>
 * MD5加密工具
 */
public class MD5Util {
    private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MD5Util() {
    }

    public static String MD5Encode(byte[] bytes) {
        String resultString = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(bytes));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return resultString;
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();

        for (int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = b + 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetName) {
        origin = origin.trim();
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
        } catch (Exception var4) {
            ;
        }

        return resultString;
    }

    public static void main(String[] args) {
        String s = "BF05CFC4B1475CA9DB8D5FE51F326EEB".toLowerCase();
        System.out.println(s);
        String sr = MD5Encode("13910000003|15020616252200001920|4563517501013883117|钟凌霜|0|430221199008282318|13910000003|" + s);
        System.out.println(sr);
    }

    public static String MD5Encode(String origin) {
        origin = origin.trim();
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception var3) {
            ;
        }

        return resultString;
    }
}