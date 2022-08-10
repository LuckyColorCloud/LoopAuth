package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.face.LoopAuthFaceImpl;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;
import com.sobercoding.loopauth.util.JsonUtil;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 登录操作
 * @create: 2022/08/08 17:27
 */
public class LoopAuthLogin {

    // 会话操作开始

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

        // TODO: 2022/8/10
        // 还有cookie没做处理

        // 根据accessModes配置项，写入上下文
        LoopAuthStrategy.getLoopAuthContext()
                .getStorage()
                .set(
                        LoopAuthStrategy.getLoopAuthConfig().getTokenName(),
                        tokenModel.getValue()
                );
        LoopAuthStrategy.getLoopAuthContext()
                .getResponse()
                .setHeader(
                        LoopAuthStrategy.getLoopAuthConfig().getTokenName(),
                        tokenModel.getValue()
                );
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
                    .removeByFacility(facilitys)
                    .setUserSession();
        }else {
            // 通过上下文获取当前用户token 注销
            getUserSessionNotNull(userId)
                    .removeByToken(LoopAuthFaceImpl.getTokenNow().getValue())
                    .setUserSession();
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
     * @Description: 创建会话
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
     * @Description: 从存储获取用户所有会话,允许为空
     * @param userId 用户id
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/9 23:05
     */
    public UserSession getUserSession(String userId){
        if (LoopAuthStrategy.getLoopAuthDao().containsKey(userId)){
            // 从存储获取用户所有会话
            return new UserSession().setUserId(userId)
                    .setTokens(
                            JsonUtil.jsonToList(LoopAuthStrategy.getLoopAuthDao().get(userId),TokenModel.class)
                    );
        }else {
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
        return new UserSession().setUserId(userId)
                .setTokens(
                        JsonUtil.jsonToList(LoopAuthStrategy.getLoopAuthDao().get(userId),TokenModel.class)
                );
    }

    /**
     * @Method: getUserSessionByUserId
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取某用户所有会话
     * @param userId 用户id
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 23:14
     */
    public UserSession getUserSessionAllByUserId(String userId) {
        return new UserSession().setUserId(userId)
                .setTokens(
                        JsonUtil.jsonToList(LoopAuthStrategy.getLoopAuthDao().get(userId),TokenModel.class)
                );
    }

    /**
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取当前用户所有会话
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 23:14
     */
    public UserSession getUserSessionAllNow() {
        String userId = getUserId();
        return getUserSessionAllByUserId(userId);
    }

    /**
     * @Method: getUserSessionNow
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取当前会话
     * @param
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public UserSession getUserSessionNow() {
        UserSession userSession = getUserSessionAllNow();
        return userSession.setTokens(
                userSession.getTokens().stream()
                        .filter(tokenModel -> getTokenNow().equals(tokenModel.getValue()))
                        .collect(Collectors.toList())
        );
    }

    /**
     * @Method: getUserId
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 提取当前用户id
     * @param
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public String getUserId(){
        // 获取token
        String token = getTokenNow().getValue();
        // 合法检测
        Optional.ofNullable(token)
                .filter(LoopAuthUtil::isNotEmpty)
                .orElseThrow(() -> new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST));

        Map<String,String> info = JsonUtil.jsonToMap(
                LoopAuthStrategy.getTokenBehavior().getInfo(token)
        );
        return info.get("userId");
    }

    /**
     * @Method: getTokenNow
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取当前token
     * @param
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public TokenModel getTokenNow(){
        return new TokenModel()
                .setValue(LoopAuthStrategy.getLoopAuthContext()
                        .getRequest()
                        .getHeader(LoopAuthStrategy.getLoopAuthConfig().getTokenName()));
    }

    /**
     * @Method: isLoginNow
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 判断当前是否登录，否则直接抛出异常
     * @param
     * @Return: boolean
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public boolean isLoginNow(){
        return LoopAuthUtil.isNotEmpty(getTokenNow().getValue());
    }


    // 会话操作结束



}
