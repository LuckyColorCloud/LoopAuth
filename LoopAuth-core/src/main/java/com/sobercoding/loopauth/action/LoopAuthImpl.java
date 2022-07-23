package com.sobercoding.loopauth.action;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XHao
 * @date 2022年07月22日 0:02
 */
public class LoopAuthImpl implements LoopAuth{

    /**
     * 密钥
     */
    private static final String SECRET = "LoopAuth";

    @Override
    public String createToken(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)// 添加头部
                //可以将基本信息放到claims中
                .withClaim("id", id)//id
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
        return token;
    }

}
