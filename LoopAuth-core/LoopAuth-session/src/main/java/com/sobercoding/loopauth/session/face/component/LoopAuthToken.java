package com.sobercoding.loopauth.session.face.component;

import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.session.SessionStrategy;
import com.sobercoding.loopauth.session.model.TokenModel;
import com.sobercoding.loopauth.util.AesUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 默认Toekn生成策略
 * 多段式token 仿JWT模式
 * @author: Sober
 */
public class LoopAuthToken {

    /**
     * 制造Token
     * @author: Sober
     * @param tokenModel token模型
     * @param secretKey 盐
     * @return java.lang.String
     */
    public String createToken(TokenModel tokenModel, String secretKey) {
        // 添加会话基本信息的base编码
        String original = tokenModel.getLoginId() +
                "," + tokenModel.getCreateTime() +
                "," + tokenModel.getTimeOut() +
                "," + tokenModel.getFacility() +
                "," + tokenModel.getFacilityName();
        return getBase64(original) +
                // 添加分隔符
                "_" +
                // 添加密文
                AesUtil.encrypted(original, secretKey);
    }

    /**
     * 验证token合法性
     * @author Sober
     * @param token token
     * @param secretKey 盐
     * @return boolean
     */
    public boolean verify(String token, String secretKey) {
        try {
            // 分割token
            String[] tokens = token.split("_");
            // 获取token包含的会话信息
            String info = getFromBase64(tokens[0]);
            // 获得原文
            String originalText = AesUtil.decode(tokens[1],secretKey);
            // 验证是否过期
            TokenModel tokenModel = getInfo(token);
            if (!SessionStrategy.getLoopAuthConfig().getTokenPersistence() && SessionStrategy.getLoopAuthConfig().getTimeOut() != -1 && (tokenModel.getCreateTime() + tokenModel.getTimeOut()) < System.currentTimeMillis()){
                throw new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST_F, "The token is due");
            }
            return info.equals(originalText);
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    /**
     * 获取token种信息
     * @author Sober
     * @param token token
     * @return com.sobercoding.loopauth.model.TokenModel
     */
    public TokenModel getInfo(String token) {
        try {
            // 分割token
            String[] tokens = token.split("_");
            String[] infos = getFromBase64(tokens[0]).split(",");
            // 获取token包含的会话信息
            return new TokenModel()
                    .setValue(token)
                    .setLoginId(infos[0])
                    .setCreateTime(Long.parseLong(infos[1]))
                    .setTimeOut(Long.parseLong(infos[2]))
                    .setFacility(infos[3])
                    .setFacilityName(infos[4]);
        }catch (RuntimeException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用Base64对字符串进行编码
     * @author Sober
     * @param str 串
     * @return java.lang.String
     */
    private String getBase64(String str) {
        byte[] b = str.getBytes(StandardCharsets.UTF_8);
        return new String(Base64.getEncoder().encode(b));
    }


    /**
     * 使用Base64对字符串进行解码
     * @author Sober
     * @param str 串
     * @return java.lang.String
     */
    private String getFromBase64(String str) {
        byte[] b;
        String result = null;
        if (str != null) {
            try {
                b = Base64.getDecoder().decode(str);
                result = new String(b, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
