package com.sobercoding;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.certificate.TokenBehavior;
import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.model.constant.TokenStyle;


/**
 * @program: ${PROJECT_NAME}
 * @author: Sober
 * @Description:
 * @create: ${YEAR}/${MONTH}/${DAY} ${HOUR}:${MINUTE}
 */
public class TokenTest {

    public static void main(String[] args) {
        // 改变token风格
        LoopAuthConfig loopAuthConfig = LoopAuthConfig.builder().tokenStyle(TokenStyle.AES126).build();
        // 装载config
        LoopAuthStrategy.setLoopAuthConfig(loopAuthConfig);
        // 获取token装载器
        TokenBehavior tokenBehavior = LoopAuthStrategy.getTokenBehavior();
        String token = tokenBehavior.createToken(
                "12312",
                LoopAuthStrategy.getLoopAuthConfig().getSecretKey()
        );
        System.out.println(token);
        String userid = tokenBehavior.decodeToken(
                tokenBehavior.createToken(
                        "12312",
                        LoopAuthStrategy.getLoopAuthConfig().getSecretKey()),
                LoopAuthStrategy.getLoopAuthConfig().getSecretKey()
        );
        System.out.println(userid);
    }
}