package com.sobercoding.loopauth.fabricate.certificate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sobercoding.loopauth.fabricate.TokenBehavior;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XHao
 * @date 2022年07月22日 0:02
 */
public class TokenJwt implements TokenBehavior {

    @Override
    public String createToken(String userId, String secretKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)// 添加头部
                //可以将基本信息放到claims中
                .withClaim("id", userId)//id
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(secretKey)); //SECRET加密
        return token;
    }

    @Override
    public String decodeToken(String token, String secretKey) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        DecodedJWT decodedJwt = jwtVerifier.verify(token);
        return decodedJwt.getClaim("id").asString();
    }
}
