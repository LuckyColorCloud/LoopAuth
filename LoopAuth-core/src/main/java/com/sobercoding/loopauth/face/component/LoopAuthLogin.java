package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;

import java.util.ArrayList;


/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 登录操作
 * @create: 2022/08/08 17:27
 */
public class LoopAuthLogin {

    /**
     * @Method: login
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录,设置终端
     * @param userId 用户id
     * @param tokenModel token模型
     * @Return: java.lang.String 返回token
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public TokenModel login(String userId,TokenModel tokenModel) {
        // 创建会话
        creationSession(userId,tokenModel);
        // 根据accessModes配置项，写入上下文
        // TODO: 2022/8/9


        // 返回token
        return tokenModel;
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
        if (facilitys.length > 0){
            // 删除所选的会话  并 刷新会话
            getUserSessionNotNull(userId)
                    .removeTokens(facilitys)
                    .setUserSession();
        }else {
            // 通过上下文获取当前用户token 注销
            // TODO: 2022/8/10
            getUserSessionNotNull(userId)
                    .removeToken("token");
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
        // 获取会话    删除所有token   刷新会话
        getUserSession(userId)
                .setTokens(new ArrayList<>())
                .setUserSession();
        return true;
    }

    /**
     * @param userId     用户id
     * @param tokenModel token模型
     * @Method: creationSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description:
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/9 23:18
     */
    public void creationSession(String userId, TokenModel tokenModel){
        // 生成token
        LoopAuthStrategy.getTokenBehavior()
                .createToken(
                        userId,
                        LoopAuthStrategy.getSecretKey.apply(userId),
                        tokenModel
                );
        // 获取存储用户的所有会话   写入当前会话   刷新会话
        getUserSession(userId)
                .setToken(tokenModel)
                .setUserSession();
    }

    /**
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 从存储获取用户所有会话
     * @param userId 用户id
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/9 23:05
     */
    public UserSession getUserSession(String userId){
        try{
            // 从存储获取用户所有会话
            return LoopAuthStrategy.getLoopAuthDao().getUserSession(userId);
        }catch (LoopAuthLoginException e){
            // 没有会话则新建会话
            return new UserSession().setUserId(userId);
        }
    }

    /**
     * @Method: getUserSessionNotNull
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 从存储获取用户所有会话,且不为空
     * @param userId 用户id
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/9 23:05
     */
    public UserSession getUserSessionNotNull(String userId){
        // 从存储获取用户所有会话
        return LoopAuthStrategy.getLoopAuthDao().getUserSession(userId);
    }


}
