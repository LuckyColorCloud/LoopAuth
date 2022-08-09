package com.sobercoding.loopauth.fabricate;

import com.sobercoding.loopauth.model.TokenModel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: Token生成/解析父类
 * @create: 2022/07/24 22:06
 */
public interface TokenBehavior {

    /**
     * 创建token
     * @Method: createToken
     * @Author: Sober
     * @param userId
     * @param secretKey
     * @return String
     * @Date: 2022/7/24 23:43
     */
    String createToken(String userId, String secretKey, TokenModel tokenModel);

    /**
     * 解析Token内userId
     * @Author: Sober
     * @Method: decodeToken
     * @param token
     * @param secretKey
     * @return String
     * @Date: 2022/7/24 23:46
     */
    String decodeToken(String token, String secretKey, TokenModel tokenModel);

}
