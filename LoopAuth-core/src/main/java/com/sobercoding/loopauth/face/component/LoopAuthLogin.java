package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.context.LoopAuthCookie;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;
import com.sobercoding.loopauth.model.constant.TokenAccessMode;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.*;


/**
 * 登录操作
 * @author: Sober
 */
public class LoopAuthLogin {

    // 可重写开始

    /**
     * 登录, 设置终端
     * @author Sober
     * @param tokenModel token模型
     */
    public void login(TokenModel tokenModel) {
        // 创建会话
        UserSession userSession = creationSession(tokenModel);

        // 写入上下文
        setContext(tokenModel);

    }

    /**
     * 登录续期
     * @author Sober
     */
    public void loginRenew() {
        // 获取老的TokenModel
        TokenModel tokenModel = getTokenModel()
                .setCreateTime(System.currentTimeMillis())
                .setTimeOut(LoopAuthStrategy.getLoopAuthConfig().getTimeOut());
        String token = tokenModel.getValue();
        // 创建新的token
        creationToken(tokenModel);
        UserSession userSession = null;
        // 开启持久化才执行
        if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistence()){
            // 获取存储用户的所有会话
            userSession = getUserSession();
            // 删除老会话
            userSession.removeToken(Collections.singleton(token));
            // 加入新会话
            userSession.setToken(tokenModel);
            // 刷新会话
            userSession.setUserSession();
        }
        // 写入上下文
        setContext(tokenModel);
    }

    /**
     * 注销某Token登录
     * @author Sober
     * @param tokenModelValues token列表
     */
    public void logout(String... tokenModelValues) {
        // 开启持久化才执行
        if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistence()){
            getUserSession().removeToken(Arrays.asList(tokenModelValues))
                    .setUserSession();
        }
        // 删除cookie
        delCookie(LoopAuthStrategy.getLoopAuthConfig().getTokenName());
    }

    /**
     * 登录校验
     * @author Sober
     */
    public void isLogin() {
        getTokenModel();
    }

    /**
     * 获取当前用户所有会话
     * @author Sober
     * @return com.sobercoding.loopauth.model.UserSession
     */
    public UserSession getUserSession() {
        return getUserSessionByLoginId(getTokenModel().getLoginId());
    }

    /**
     * 获取当前请求的token模型
     * @author Sober
     * @return com.sobercoding.loopauth.model.TokenModel
     */
    public TokenModel getTokenModel() {
        // 优先从会话存储获取
        TokenModel strategyTokenModel = (TokenModel) LoopAuthStrategy.getLoopAuthContext().getStorage()
                .get("tokenModel");
        // 会话存储不存在则从上下文请求体获取
        if (LoopAuthUtil.isEmpty(strategyTokenModel)){
            // 从请求体获取携带的token
            String token = getBodyToken();
            // 解析token参数
            TokenModel tokenModel = LoopAuthStrategy.getLoopAuthToken().getInfo(token);
            LoopAuthLoginException.isEmpty(tokenModel,LoopAuthExceptionEnum.LOGIN_NOT_EXIST);
            // 开启持久化才执行
            if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistence()){
                // 验证存储中是否存在
                tokenModel = Optional.ofNullable(tokenModel.gainTokenModel())
                        .orElseThrow(() -> new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST));
                // 查看是否过期
                if (isExpire(tokenModel)){
                    // 注销 当前token
                    tokenModel.remove();
                    // 删除cookie
                    delCookie(LoopAuthStrategy.getLoopAuthConfig().getTokenName());
                    throw new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_EXPIRE);
                }
                // 写入当前会话Context存储器
            }
            // 写入当前会话Context存储器
            LoopAuthStrategy.getLoopAuthContext().getStorage().set("tokenModel",tokenModel);
            // token续期
            if (LoopAuthStrategy.getLoopAuthConfig().getRenew()){
                loginRenew();
            }
            return tokenModel;
        }
        return strategyTokenModel;
    }

    // 可重写结束


    /**
     * 创建会话，创建token写入缓存
     * @author Sober
     * @param tokenModel token模型
     * @return com.sobercoding.loopauth.model.UserSession
     */
    private UserSession creationSession(TokenModel tokenModel) {
        // 生成token
        creationToken(tokenModel);
        // 开启持久化才存储userSession
        if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistence()){
            // 获取存储用户的所有会话   写入当前会话   刷新会话
            UserSession userSession = getUserSessionByLoginId(tokenModel.getLoginId());
            userSession.setToken(tokenModel)
                    .setUserSession();
            return userSession;
        }
        return null;
    }

    private void creationToken(TokenModel tokenModel){
        // 生成token载入到tokenModel
        String token = LoopAuthStrategy.getLoopAuthToken()
                .createToken(
                        tokenModel,
                        LoopAuthStrategy.getSecretKey.apply(tokenModel.getLoginId())
                );
        tokenModel.setValue(token);
    }

    /**
     * 从存储获取用户所有会话
     * @author Sober
     * @param loginId 用户id
     * @return com.sobercoding.loopauth.model.UserSession
     */
    private UserSession getUserSessionByLoginId(String loginId) {
        return new UserSession().setLoginId(loginId)
                .getUserSession();
    }


    /**
     * 从请求体获取token  通过合法认证的token但未必有效
     * @author Sober
     * @return java.lang.String
     */
    private String getBodyToken() {
        // 从请求体获取携带的token
        String token = null;
        for (TokenAccessMode tokenAccessMode : LoopAuthStrategy.getLoopAuthConfig().getAccessModes()){
            switch (tokenAccessMode){
                case COOKIE:
                    token = LoopAuthStrategy.getLoopAuthContext()
                            .getRequest()
                            .getCookieValue(LoopAuthStrategy.getLoopAuthConfig().getTokenName());
                    break;
                case HEADER:
                    token = LoopAuthStrategy.getLoopAuthContext()
                            .getRequest()
                            .getHeader(LoopAuthStrategy.getLoopAuthConfig().getTokenName());
                    break;
                default:
                    break;
            }
            // 如果取到值则 不继续执行
            if (LoopAuthUtil.isNotEmpty(token)){
                break;
            }
        }
        // 不为空
        LoopAuthLoginException.isEmpty(token,LoopAuthExceptionEnum.LOGIN_NOT_EXIST);
        // 解析token参数
        TokenModel tokenBodyModel = LoopAuthStrategy.getLoopAuthToken().getInfo(token);
        LoopAuthLoginException.isEmpty(tokenBodyModel,LoopAuthExceptionEnum.LOGIN_NOT_EXIST);
        // token合法验证
        LoopAuthLoginException.isTrue(
                LoopAuthStrategy.getLoopAuthToken().verify(token, LoopAuthStrategy.getSecretKey.apply(tokenBodyModel.getLoginId())),
                LoopAuthExceptionEnum.LOGIN_NOT_EXIST);
        return token;
    }


    /**
     * token是否过期
     * @author Sober
     * @param tokenModel token模型
     * @return boolean
     */
    private boolean isExpire(TokenModel tokenModel) {
        if (LoopAuthStrategy.getLoopAuthConfig().getTimeOut() != -1){
            return tokenModel.getCreateTime() + tokenModel.getTimeOut() < System.currentTimeMillis();
        }
        return false;
    }

    /**
     * @author Sober
     * @param tokenModel token模型
     * @return void
     */
    private void setContext(TokenModel tokenModel){
        // 如果选择了COOKIE获取  则 写入Response的Cookie存储
        if (LoopAuthStrategy.getLoopAuthConfig()
                .getAccessModes().stream()
                .anyMatch(tokenAccessMode -> tokenAccessMode == TokenAccessMode.COOKIE)){
            // 如果选择了COOKIE获取  则 写入Response的Cookie存储
            if (LoopAuthStrategy.getLoopAuthConfig()
                    .getAccessModes().stream()
                    .anyMatch(tokenAccessMode -> tokenAccessMode == TokenAccessMode.COOKIE)){
                LoopAuthCookie cookie = new LoopAuthCookie()
                        .setName(LoopAuthStrategy.getLoopAuthConfig().getTokenName())
                        .setValue(tokenModel.getValue());
                LoopAuthStrategy.getLoopAuthContext().getResponse()
                        .addHeader("Set-Cookie",cookie.toCookieString());
            }
        }

        // 如果选择了HEADER获取  则 写入Response头返回
        if (LoopAuthStrategy.getLoopAuthConfig()
                .getAccessModes().stream()
                .anyMatch(tokenAccessMode -> tokenAccessMode == TokenAccessMode.HEADER)){
            LoopAuthStrategy.getLoopAuthContext().getResponse().setHeader(
                    LoopAuthStrategy.getLoopAuthConfig().getTokenName(),
                    tokenModel.getValue()
            );
        }
        // 写入当前会话Context存储器
        LoopAuthStrategy.getLoopAuthContext().getStorage().set("tokenModel",tokenModel);
    }

    /**
     * 删除cookie
     * @author Sober
     * @param name 名字
     */
    private void delCookie(String name){
        // 如果选择了COOKIE获取  则 写入Response的Cookie存储
        if (LoopAuthStrategy.getLoopAuthConfig()
                .getAccessModes().stream()
                .anyMatch(tokenAccessMode -> tokenAccessMode == TokenAccessMode.COOKIE)){
            LoopAuthCookie cookie = new LoopAuthCookie()
                    .setName(name)
                    .setValue("None")
                    .setMaxAge(0);
            LoopAuthStrategy.getLoopAuthContext().getResponse()
                    .addHeader("Set-Cookie",cookie.toCookieString());
        }
    }
}
