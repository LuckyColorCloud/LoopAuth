package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.session.carryout.component.LoopAuthToken;
import com.sobercoding.loopauth.session.model.TokenModel;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/09 15:37
 */
public class MyLoopAuthToken extends LoopAuthToken {
    /**
     * @Method: createToken
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 制造Token
     * @param info 会话信息
     * @param secretKey 盐
     * @Return: String 返回token
     * @Exception:
     * @Date: 2022/8/12 22:26
     */
    @Override
    public String createToken(TokenModel info, String secretKey) {
        // 添加会话基本信息的base编码
        return "";
    }

    /**
     * @Method: verify
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 验证token合法性
     * @param token token
     * @param secretKey 盐
     * @Return: void
     * @Exception:
     * @Date: 2022/8/12 22:46
     */
    @Override
    public boolean verify(String token, String secretKey) {
        return true;
    }
}
