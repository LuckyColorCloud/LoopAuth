package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.fabricate.TokenBehavior;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/09 15:37
 */
public class MyToken implements TokenBehavior {
    @Override
    public String createToken(String userId, String secretKey) {
        return "sober";
    }

    @Override
    public String decodeToken(String token, String secretKey) {
        return "sober";
    }
}
