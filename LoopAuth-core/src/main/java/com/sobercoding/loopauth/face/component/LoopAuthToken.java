package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.util.AesUtil;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 默认Toekn生成策略
 * 多段式token 仿JWT模式
 * @create: 2022/08/12 22:20
 */
public class LoopAuthToken {

    /**
     * @Method: createToken
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 制造Token
     * @param tokenModel token模型
     * @param secretKey 盐
     * @Return: String 返回token
     * @Exception:
     * @Date: 2022/8/12 22:26
     */
    public String createToken(TokenModel tokenModel, String secretKey) {
        // 添加会话基本信息的base编码
        String original = tokenModel.getLoginId() +
                "," + tokenModel.getCreateTime() +
                "," + tokenModel.getTimeOut() +
                "," + tokenModel.getFacility();
        return getBase64(original) +
                // 添加分隔符
                "_" +
                // 添加密文
                AesUtil.encrypted(original, secretKey);
    }

    /**
     * @Method: verify
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 验证token合法性
     * @param token token
     * @param secretKey 盐
     * @Return: void
     * @Exception:
     * @Date: 2022/8/12 22:46
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
            if (!LoopAuthStrategy.getLoopAuthConfig().getTokenPersistence() && LoopAuthStrategy.getLoopAuthConfig().getTimeOut() != -1 && (tokenModel.getCreateTime() + tokenModel.getTimeOut()) < System.currentTimeMillis()){
                throw new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_EXPIRE);
            }
            return info.equals(originalText);
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    /**
     * @Method: getInfo
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取token种信息
     * @param token
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Exception:
     * @Date: 2022/8/12 22:47
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
                    .setFacility(infos[3]);
        }catch (RuntimeException e){
            return null;
        }
    }

    /**
     * @Method: getBASE64
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 使用Base64对字符串进行编码
     * @param str
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/12 23:00
     */
    private String getBase64(String str) {
        byte[] b = str.getBytes(StandardCharsets.UTF_8);
        return new String(Base64.getEncoder().encode(b));
    }


    /**
     * @Method: getFromBASE64
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 使用Base64对字符串进行解码
     * @param str
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/12 23:00
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
