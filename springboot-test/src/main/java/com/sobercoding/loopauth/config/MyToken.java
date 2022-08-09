package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.fabricate.TokenBehavior;
import com.sobercoding.loopauth.model.TokenModel;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/09 15:37
 */
public class MyToken implements TokenBehavior {
    @Override
    public void createToken(String userId, String secretKey, TokenModel tokenModel) {
        tokenModel.setValue("sober");
    }

    @Override
    public boolean decodeToken(String token, String secretKey) {
        return true;
    }
}
