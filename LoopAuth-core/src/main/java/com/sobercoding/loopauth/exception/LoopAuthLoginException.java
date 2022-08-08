package com.sobercoding.loopauth.exception;

import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.util.JsonUtil;

import java.util.Optional;
import java.util.Set;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 会话异常
 * @create: 2022/08/08 19:24
 */
public class LoopAuthLoginException extends LoopAuthException{

    private static final long serialVersionUID = 1L;

    public LoopAuthLoginException(LoopAuthExceptionEnum loopAuthExceptionEnum) {
        super(loopAuthExceptionEnum);
    }



    /**
     * @Method: isNotLogin
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 判断会话是否合规，
     * 合规则返回TokenModelSet，不合规则抛出异常
     * @param tokens 会话的tokens
     * @Return: Set<TokenModel>
     * @Exception:
     * @Date: 2022/8/8 23:42
     */
    public static Set<TokenModel> isSessionLegality(String tokens){
        return JsonUtil.jsonToList(
                Optional.ofNullable(tokens)
                        .orElseThrow(() -> new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST)),
                TokenModel.class);
    }
}
