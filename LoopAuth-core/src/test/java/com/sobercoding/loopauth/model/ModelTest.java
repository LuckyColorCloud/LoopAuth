package com.sobercoding.loopauth.model;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
                .value("1sadasdasdas")
                .build();
        UserSession userSession = UserSession.builder()
                .userId("1")
                .tokenModel(tokenModel)
                .tokenModel(tokenMode2)
                .build();
    }
}
