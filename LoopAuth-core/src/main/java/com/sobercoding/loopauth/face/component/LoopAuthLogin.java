package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;

import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/08 17:27
 */
public class LoopAuthLogin {

    /**
     * @Method: login
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录,设置终端
     * @param userId 用户id
     * @param facility 终端类型
     * @Return: java.lang.String 返回token
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public String login(String userId,String facility) {
        // 生成token
        String token = LoopAuthStrategy
                .getTokenBehavior()
                .createToken(
                        userId,
                        LoopAuthStrategy.getSecretKey.apply(userId)
                );
        System.out.println(token);
        // 会话对象
        UserSession userSession;
        try{
            // 获取会话
            userSession = LoopAuthStrategy.getLoopAuthDao().getUserSession(userId);
        }catch (LoopAuthLoginException e){
            // 没有会话则新建会话
            userSession = new UserSession()
                    .setUserId(userId);
        }
        // 增加会话token
        TokenModel tokenModel = new TokenModel(token,facility);
        userSession.getTokens().add(tokenModel);
        // 持久化
        LoopAuthStrategy.getLoopAuthDao().setUserSession(userSession);
        // 返回token
        return token;
    }

    /**
     * @Method: logout
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注销某终端登录
     * @param userId 用户id
     * @param facilitys 终端类型 可多输入
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public boolean logout(String userId,String... facilitys) {
        // 会话对象
        UserSession userSession = LoopAuthStrategy.getLoopAuthDao().getUserSession(userId);
        // 删除所选的会话
        userSession.setTokens(
                userSession.getTokens().stream().filter(
                        tokenModel -> !Arrays.asList(facilitys)
                                .contains(tokenModel.getFacility())
                ).collect(Collectors.toSet())
        );
        // 如果已经不存在会话则删除全部
        if (userSession.getTokens().size() <= 0){
            // 删除会话
            LoopAuthStrategy.getLoopAuthDao().removeUserSession(userId);
        }else {
            // 重新写入会话
            LoopAuthStrategy.getLoopAuthDao().setUserSession(userSession);
        }
        return true;
    }

    /**
     * @Method: logout
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注销所有登录状态
     * @param userId 用户id
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public boolean logoutAll(String userId) {
        // 检查会话对象是否存在
        try {
            LoopAuthStrategy.getLoopAuthDao().getUserSession(userId);
        }catch (LoopAuthLoginException e){
            e.printStackTrace();
        }
        // 删除会话
        LoopAuthStrategy.getLoopAuthDao().removeUserSession(userId);
        return true;
    }

}
