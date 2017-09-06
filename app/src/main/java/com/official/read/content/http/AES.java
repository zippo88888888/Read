package com.official.read.content.http;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 正式环境使用https，U也不一样
 */
public final class AES {

    private static final String U = "75EDC5DC54546F55DFF52C7415683816CAB0401FA98042A7E404293766525AA5D050A9EF8EC" +
            "57AA844352579964AC2E3D338D86BC3AD7ABD8C648A7CF90353C0";

    public static LinkedHashMap<String, String> getSign(LinkedHashMap<String, String> res) {
        if (res == null || res.isEmpty()) return res;
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (String key : res.keySet()) {
            if (i != 0) sb.append("&");
            sb.append(key);
            sb.append("=");
            sb.append(res.get(key));
            i++;
        }
        String param = sb.toString();
        String sign = getSign(param);
        res.put("sign", sign);
        return res;
    }

    public static String getSign(String content) {
        String sign = encrypts(content);
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(sign.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String encrypts(String content) {
        String res;
        try {
            SecretKeySpec key = getSecretKeySpec();
            AlgorithmParameterSpec spec = getAlgorithmParameterSpec();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] result = cipher.doFinal(byteContent);
            res = new String(Base64.encode(result, Base64.DEFAULT), "UTF-8");
            res = replaceBlank(res);
            return res; // 加密
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static AlgorithmParameterSpec getAlgorithmParameterSpec() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    private static SecretKeySpec getSecretKeySpec() {
        SecretKeySpec key = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(U.getBytes("UTF-8"));
            byte[] keyBytes = new byte[32];
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
            key = new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * 不换行
     */
    private static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
