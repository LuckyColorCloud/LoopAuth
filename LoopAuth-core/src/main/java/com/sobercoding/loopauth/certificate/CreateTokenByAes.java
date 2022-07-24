package com.sobercoding.loopauth.certificate;

import com.sobercoding.loopauth.util.HexUtil;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @ClassName CreateTokenByAes
 * @Description AES生成token
 * @Author gezi
 * @Email a1151575020@163.com
 * @Since 2022/7/23 19:54
 * @Version 1.0
 */

public class CreateTokenByAes {

    /**
     * 秘钥
     */
    private String key;

    /**
     * 加密算法
     */
    private static final String ALGORITHM = "AES";

    private static final String UTF8 = StandardCharsets.UTF_8.name();


    public CreateTokenByAes(String key) {
        this.key = key;
    }

    public  String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    /**
     * @param id:
     * @Author gezi
     * @Description: 根据ID加密
     * @Since 2022/7/23 20:30
     * @return java.lang.String
     */
    public String encrypt(String id) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, key);
        byte[] encodedBytes = cipher.doFinal(id.getBytes(UTF8));
        //转十六进制
        return HexUtil.encodeHexStr(encodedBytes);
    }
    /**
     * @param encodedText: 加密后的字符串
     * @Author gezi
     * @Description: 解密
     * @Since 2022/7/23 20:47
     * @return java.lang.String
     */
    public String decrypt(String encodedText) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        //转二进制
        byte[] bytes = HexUtil.decodeHex(encodedText);
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(bytes);
        return new String(decryptedBytes,UTF8);
    }
    /**
     * @param type:
     * @param seed:
     * @Author gezi
     * @Description: 获取cipher对象
     * @Since 2022/7/23 20:44
     * @return javax.crypto.Cipher
     */
    private Cipher getCipher(int type,String seed) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        //获取实例
        Cipher cipher = Cipher.getInstance("AES");

        //创建对象,可以根据传入的key生成一个指定长度的key
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
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

}
