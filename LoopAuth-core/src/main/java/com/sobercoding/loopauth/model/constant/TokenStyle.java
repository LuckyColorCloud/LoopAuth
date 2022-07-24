package com.sobercoding.loopauth.model.constant;

import com.sobercoding.loopauth.certificate.TokenAes;
import com.sobercoding.loopauth.certificate.TokenBehavior;
import com.sobercoding.loopauth.certificate.TokenJwt;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/24 22:22
 */
public enum TokenStyle {

    /**
     * JWT风格Token
     */
    JWT,
    /**
     * AES加密风格Token
     */
    AES126;

    public TokenBehavior getBean() {
        if (this.equals(TokenStyle.JWT)){
            return new TokenJwt();
        }else {
            return new TokenAes();
        }
    }
}
