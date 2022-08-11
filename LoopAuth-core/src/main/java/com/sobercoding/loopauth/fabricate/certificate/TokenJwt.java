package com.sobercoding.loopauth.fabricate.certificate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sobercoding.loopauth.fabricate.TokenBehavior;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.util.JsonUtil;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XHao
 * @date 2022年07月22日 0:02
 */
public class TokenJwt implements TokenBehavior {

    @Override
    public void createToken(String userId, String secretKey, TokenModel tokenModel) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部信息
                .withHeader(map)
                // 用户id
                .withClaim("userId", userId)
                // 设备
                .withClaim("facility", tokenModel.getFacility())
                // 签发时间
                .withIssuedAt(new Date(tokenModel.getCreateTime()))
                // 有效期
                .withExpiresAt(new Date(tokenModel.getCreateTime() + tokenModel.getTimeOut()))
                // SECRET加密
                .sign(Algorithm.HMAC256(secretKey));
        // 写入token
        tokenModel.setValue(token);
    }

    @Override
    public boolean decodeToken(String token, String secretKey) {
        // TODO: 2022/8/11
//        需要处理异常
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        DecodedJWT decodedJwt = jwtVerifier.verify(token);
        decodedJwt.getClaim("userId").asString();
        return true;
    }

    @Override
    public Map<String, String> getInfo(String token) {
        // TODO: 2022/8/11
//        需要处理异常
        String[] tokens = token.split("\\.");
        byte[] infoBytes = java.util.Base64.getDecoder().decode(tokens[1]);
        String infoStr = new String(infoBytes);
        return JsonUtil.jsonToMap(infoStr);
    }
}
