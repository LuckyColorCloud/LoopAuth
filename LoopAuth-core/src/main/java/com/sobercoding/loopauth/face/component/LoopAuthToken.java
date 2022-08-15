package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.util.AesUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.nio.charset.StandardCharsets;
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
     * @param info 会话信息
     * @param secretKey 盐
     * @Return: String 返回token
     * @Exception:
     * @Date: 2022/8/12 22:26
     */
    public String createToken(Object info, String secretKey) {
        HashMap<String,String> infoMap = (HashMap<String, String>) info;
        // 添加会话基本信息的base编码
        String original = infoMap.get("loginId") + "," + infoMap.get("createTime");
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
            return info.equals(originalText);
        }catch (RuntimeException e){
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
    public Object getInfo(String token) {
        try {
            // 分割token
            String[] tokens = token.split("_");
            Map<String,String> info = new HashMap<>(4);
            String[] infos = getFromBase64(tokens[0]).split(",");
            // 获取token包含的会话信息
            info.put("loginId",infos[0]);
            info.put("createTime",infos[1]);
            return info;
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
        return new BASE64Encoder().encode(b);
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
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(str);
                result = new String(b, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
