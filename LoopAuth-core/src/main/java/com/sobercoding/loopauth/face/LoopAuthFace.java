package com.sobercoding.loopauth.face;

import com.sobercoding.loopauth.LoopAuthStrategy;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/30 23:46
 */
public class LoopAuthFace {

    public static String login() {
        return LoopAuthStrategy.getLoopAuthConfig().getSecretKey();
    }
}
