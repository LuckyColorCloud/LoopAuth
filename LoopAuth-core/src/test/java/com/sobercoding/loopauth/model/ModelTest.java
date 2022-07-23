package com.sobercoding.loopauth.model;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/23 21:56
 */
public class ModelTest {

    public static void main(String[] args) throws IOException {
        // 创建token模型
        TokenModel tokenModel = TokenModel.builder()
                //过期时间毫秒单位
                .timeOut(10000000)
                //创建时间
                .createTime(System.currentTimeMillis())
                //设备型号
                .facility("PC")
                //token
                .value("sakdjhaskjhdiouqwyeoqwie")
                .build();
        TokenModel tokenMode2 = TokenModel.builder()
                //过期时间毫秒单位
                .timeOut(10000000)
                //创建时间
                .createTime(System.currentTimeMillis())
                //设备型号
                .facility("PC")
                //token
                .value("sakdjhaskjhdiouqwyeoqwi123e")
                .build();
        UserSession userSession = UserSession.builder()
                .userId("1")
                .tokenModel(tokenModel)
                .tokenModel(tokenMode2)
                .build();
        System.out.println(userSession.getTokens().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> params = new HashMap<>();
        params.put("userId",userSession.getUserId());
        params.put("tokens",userSession.getTokens());

        //将对象转为JSON串
        String jsonString = objectMapper.writeValueAsString(params);
        System.out.println(jsonString);
        System.out.println("--------------------分割线-----------------------------");

        //将JSON串 转为 Object 对象
        Map resultMap = objectMapper.readValue(jsonString, HashMap.class);
        Set<TokenModel> tokenModels = new ConcurrentSkipListSet<>();
        for (TokenModel tokenModel1 : (List<TokenModel>) resultMap.get("tokens")){
            tokenModels.add(tokenModel1);
        }
        System.out.println(tokenModels);
//        Set<TokenModel> tokenModels =
    }
}
