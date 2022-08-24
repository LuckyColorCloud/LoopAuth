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

        // 写入会话Context存储器
        LoopAuthStrategy.getLoopAuthContext().getStorage().set("userSession",userSession);

    }

    /**
     * 登录续期
     * @author Sober
     */
    public void loginRenew() {
        // 获取老的TokenModel 并刷新过期时间
        TokenModel tokenModel = getTokenModel()
                .setCreateTime(System.currentTimeMillis())
                .setTimeOut(LoopAuthStrategy.getLoopAuthConfig().getTimeOut());
        // 开启持久化才执行
        if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistence()){
            // 更新会话过期时间
            getUserSession().setToken(tokenModel);
        }else {
            // 创建新的token
            creationToken(tokenModel);
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
            getUserSession().removeToken(Arrays.asList(tokenModelValues));
        }
        // 删除cookie
        delCookie(LoopAuthStrategy.getLoopAuthConfig().getTokenName());
    }

    /**
     * 登录校验
     * @author Sober
     */
    public void isLogin() {
        // 从请求体载入当前会话
        getBodyToken();
        // token续期
        if (LoopAuthStrategy.getLoopAuthConfig().getRenew()){
            loginRenew();
        }
    }

    /**
     * 获取当前用户所有会话
     * @author Sober
     * @return com.sobercoding.loopauth.model.UserSession
     */
    public UserSession getUserSession() {
        return (UserSession) LoopAuthStrategy
                .getLoopAuthContext().getStorage().get("userSession");
    }

    /**
     * 获取当前请求的token模型
     * @author Sober
     * @return com.sobercoding.loopauth.model.TokenModel
     */
    public TokenModel getTokenModel() {
        return getUserSession().getTokenModelNow();
    }

    // 可重写结束


    /**
     * 创建会话，创建token写入缓存
     * @author Sober
     * @param tokenModel token模型
     * @return com.sobercoding.loopauth.model.UserSession
     */
    private UserSession creationSession(TokenModel tokenModel) {
        UserSession userSession = new UserSession();
        // 生成token
        creationToken(tokenModel);
        // 开启持久化才存储userSession
        if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistence()){
            // 获取存储用户的所有会话   写入当前会话   刷新会话
            userSession = getUserSessionByLoginId(tokenModel.getLoginId());
            userSession.setToken(tokenModel);
        }
        userSession.setTokenModelNow(tokenModel);
        return userSession;
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
                .gainUserSession();
    }


    /**
     * 从请求体获取token  通过合法认证的token
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
        UserSession userSession = new UserSession()
                .setTokenModelNow(tokenBodyModel);
        // 开启持久化执行
        if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistence()){
            // 从内存获取loginId  后  刷新userSession存储 最后刷新当前TokenModel 生成持久化的合法会话
            userSession.setLoginId(userSession.getTokenModelNow().gainLongId())
                    .gainUserSession()
                    .gainModelByToken(userSession.getTokenModelNow().getValue());
            // 验证存储中是否存在
            Optional.ofNullable(userSession.getTokenModelNow())
                    .orElseThrow(() -> new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST));
            // 查看是否过期
            if (isExpire(userSession.getTokenModelNow())){
                // 注销 当前token
                getUserSession().removeToken(Collections.singleton(userSession.getTokenModelNow().getValue()));
                // 删除cookie
                delCookie(LoopAuthStrategy.getLoopAuthConfig().getTokenName());
                throw new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_EXPIRE);
            }
        }
        // 存储
        LoopAuthStrategy.getLoopAuthContext().getStorage().set(
                "userSession",
                userSession);
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
