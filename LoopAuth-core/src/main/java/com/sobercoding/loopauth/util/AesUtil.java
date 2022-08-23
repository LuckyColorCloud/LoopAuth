package com.sobercoding.loopauth.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密
 * @author: Sober
 */
public class AesUtil {

    /**
     * 加密算法
     */
    private static final String ALGORITHM = "AES";

    /**
     * 算法
     */
    private static final String SHA1PRNG = "SHA1PRNG";


    /**
     * 编码类型
     */
    private static final String UTF8 = StandardCharsets.UTF_8.name();

    private static final char[] HEX_CHARS = {
            '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
    };

    private static final int TWOSING = 2;

    /**
     * 2转16
     * @param bytes: 二进制数组
     * @author gezi
     * @return java.lang.String
     */
    public static String encodeHexStr(byte[] bytes){
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            buf[index++] = HEX_CHARS[b >>> 4 & 0xf];
            buf[index++] = HEX_CHARS[b & 0xf];
        }
        return new String(buf);
    }

    /**
     * 16转2
     * @param hexStr: 十六进制字符串
     * @author gezi
     * @return byte[]
     */
    public static byte[] decodeHex(String hexStr){
        if (hexStr == null || hexStr.length() == 0){
            return null;
        }
        if (hexStr.length() % TWOSING != 0){
            hexStr = "0" + hexStr;
        }
        char[] chars = hexStr.toCharArray();
        int len = chars.length / 2;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            int x = i * 2;
            bytes[i] = (byte)Integer.parseInt(
                    String.valueOf(new char[]{chars[x],chars[x + 1]}),16
            );
        }
        return bytes;
    }

    /**
     * 获取cipher对象
     * @author gezi
     * @return javax.crypto.Cipher
     */
    private static Cipher getCipher(int type,String seed) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        //获取实例
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        //创建对象,可以根据传入的key生成一个指定长度的key
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
        secureRandom.setSeed(seed.getBytes(UTF8));
        keyGenerator.init(128,secureRandom);

        //生成新秘钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获取到新秘钥的字节数组
        byte[] encoded = secretKey.getEncoded();
        SecretKey secretKeySpec = new SecretKeySpec(encoded, ALGORITHM);
        cipher.init(type,secretKeySpec);
        return cipher;
    }

    /**
     * 加密文本
     * @author Sober
     * @param originalText 原文
     * @param secretKey 盐
     * @return java.lang.String
     */
    public static String encrypted(String originalText, String secretKey){
        byte[] encodedBytes;
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, secretKey);
            encodedBytes = cipher.doFinal(originalText.getBytes(UTF8));
        } catch (NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        //转十六进制 写入token
        return encodeHexStr(encodedBytes);
    }

    /**
     * 解码密文
     * @author Sober
     * @param ciphertext 密文
     * @param secretKey 盐
     * @return java.lang.String
     */
    public static String decode(String ciphertext, String secretKey){
        byte[] decryptedBytes;
        String info;
        try {
            byte[] bytes = decodeHex(ciphertext);
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE, secretKey);
            decryptedBytes = cipher.doFinal(bytes);
            info = new String(decryptedBytes,UTF8);
        } catch (NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return info;
    }


}
