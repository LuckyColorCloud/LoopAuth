package com.sobercoding.loopauth.fabricate.certificate;

import com.sobercoding.loopauth.fabricate.TokenBehavior;
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

public class TokenAes implements TokenBehavior {

    /**
     * 加密算法
     */
    private static final String ALGORITHM = "AES";

    /**
     * 编码类型
     */
    private static final String UTF8 = StandardCharsets.UTF_8.name();

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

    @Override
    public String createToken(String userId, String secretKey){
        byte[] encodedBytes;
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, secretKey);
            encodedBytes = cipher.doFinal(userId.getBytes(UTF8));
        } catch (NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        //转十六进制
        return HexUtil.encodeHexStr(encodedBytes);
    }

    @Override
    public String decodeToken(String token, String secretKey){
        byte[] decryptedBytes;
        String userId;
        try {
            byte[] bytes = HexUtil.decodeHex(token);
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE, secretKey);
            decryptedBytes = cipher.doFinal(bytes);
            userId = new String(decryptedBytes,UTF8);
        } catch (NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        //转二进制
        return userId;
    }
}
