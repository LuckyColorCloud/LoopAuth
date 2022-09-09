package com.sobercoding.loopauth.session.face.component;

import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.session.SessionStrategy;
import com.sobercoding.loopauth.session.context.LoopAuthCookie;
import com.sobercoding.loopauth.session.model.TokenAccessMode;
import com.sobercoding.loopauth.session.model.TokenModel;
import com.sobercoding.loopauth.session.model.UserSession;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Optional;

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
        SessionStrategy.getLoopAuthContext().getStorage().set("userSession", userSession);
        SessionStrategy.getLoopAuthContext().getStorage().set("isLogin", true);

    }

    /**
     * 登录续期
     * @author Sober
     */
    public void loginRenew() {
        // 获取老的TokenModel 并刷新过期时间
        TokenModel tokenModel = getTokenModel()
                .setCreateTime(System.currentTimeMillis())
                .setTimeOut(SessionStrategy.getLoopAuthConfig().getTimeOut());
        // 开启持久化才执行
        if (SessionStrategy.getLoopAuthConfig().getTokenPersistence()){
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
     */
    public void logout() {
        // 开启持久化才执行
        if (SessionStrategy.getLoopAuthConfig().getTokenPersistence()){
            getUserSession().removeToken(getTokenModel().getValue());
        }
        // 删除会话存储
        SessionStrategy.getLoopAuthContext().getStorage().delete("userSession");
        // 删除登录状态
        SessionStrategy.getLoopAuthContext().getStorage().delete("isLogin");
        // 删除cookie
        delCookie(SessionStrategy.getLoopAuthConfig().getTokenName());
    }

    /**
     * 注销当前会话所属loginId所有登录
     * @author Sober
     */
    public void logoutAll() {
        // 开启持久化才执行
        if (SessionStrategy.getLoopAuthConfig().getTokenPersistence()){
            getUserSession().remove();
        }
        // 删除会话存储
        SessionStrategy.getLoopAuthContext().getStorage().delete("userSession");
        // 删除登录状态
        SessionStrategy.getLoopAuthContext().getStorage().delete("isLogin");
        // 删除cookie
        delCookie(SessionStrategy.getLoopAuthConfig().getTokenName());
    }

    /**
     * 登录校验
     * @author Sober
     */
    public void isLogin() {
        if (SessionStrategy.getLoopAuthContext().getStorage().get("isLogin") == null){
            // 从请求体载入当前会话
            loadUserSession();
            // token续期
            if (SessionStrategy.getLoopAuthConfig().getRenew()){
                loginRenew();
            }
        }
    }

    /**
     * 获取当前用户所有会话
     * @author Sober
     * @return com.sobercoding.loopauth.model.UserSession
     */
    public UserSession getUserSession() {
        return (UserSession) SessionStrategy
                .getLoopAuthContext().getStorage().get("userSession");
    }

    /**
     * 从存储获取指定用户所有会话
     * @author Sober
     * @param loginId 用户id
     * @return com.sobercoding.loopauth.model.UserSession
     */
    public UserSession getUserSessionByLoginId(String loginId) {
        return new UserSession().setLoginId(loginId)
                .gainUserSession();
    }

    /**
     * 获取当前请求的token模型
     * @author Sober
     * @return com.sobercoding.loopauth.model.TokenModel
     */
    public TokenModel getTokenModel() {
        return getUserSession().getTokenModelNow();
    }

    /**
     * 获取当前请求的token模型
     * @author Sober
     * @return com.sobercoding.loopauth.model.TokenModel
     */
    public TokenModel getTokenModelByTokenValue(String token) {
        return new UserSession()
                .setTokenModelNow(new TokenModel().setValue(token))
                .gainUserSession()
                .getTokenModelNow();
    }

    /**
     * 强制指定token离线
     * @author Sober
     * @param tokens token值列表
     */
    public void forcedOfflineByToken(String... tokens) {
        new UserSession()
                .setTokenModelNow(new TokenModel().setValue(tokens[0]))
                .gainUserSession()
                .removeToken(tokens);
    }

    /**
     * 强制指定用户所有会话离线
     * @author Sober
     * @param loginId 用户id
     */
    public void forcedOfflineByLoginId(String loginId) {
        new UserSession()
                .setLoginId(loginId)
                .gainUserSession()
                .remove();
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
        if (SessionStrategy.getLoopAuthConfig().getTokenPersistence()){
            // 获取存储用户的所有会话   写入当前会话   刷新会话
            userSession = getUserSessionByLoginId(tokenModel.getLoginId());
            userSession.setToken(tokenModel);
        }
        userSession.setTokenModelNow(tokenModel);
        return userSession;
    }

    private void creationToken(TokenModel tokenModel){
        // 生成token载入到tokenModel
        String token = SessionStrategy.getLoopAuthToken()
                .createToken(
                        tokenModel,
                        SessionStrategy.getSecretKey.apply(tokenModel.getLoginId())
                );
        tokenModel.setValue(token);
    }

    /**
     * 从请求体获取token  通过合法认证的token
     * @author Sober
     * @return java.lang.String
     */
    private void loadUserSession() {
        // 从请求体获取携带的token
        String token = null;
        for (TokenAccessMode tokenAccessMode : SessionStrategy.getLoopAuthConfig().getAccessModes()){
            switch (tokenAccessMode){
                case COOKIE:
                    token = SessionStrategy.getLoopAuthContext()
                            .getRequest()
                            .getCookieValue(SessionStrategy.getLoopAuthConfig().getTokenName());
                    break;
                case HEADER:
                    token = SessionStrategy.getLoopAuthContext()
                            .getRequest()
                            .getHeader(SessionStrategy.getLoopAuthConfig().getTokenName());
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
        LoopAuthLoginException.isEmpty(token, LoopAuthExceptionEnum.LOGIN_NOT_EXIST_F, "Failed to obtain Token");
        // 解析token参数
        TokenModel tokenBodyModel = SessionStrategy.getLoopAuthToken().getInfo(token);
        LoopAuthLoginException.isEmpty(tokenBodyModel,LoopAuthExceptionEnum.LOGIN_NOT_EXIST_F, "Token illegal");
        // token合法验证
        LoopAuthLoginException.isTrue(
                SessionStrategy.getLoopAuthToken().verify(token, SessionStrategy.getSecretKey.apply(tokenBodyModel.getLoginId())),
                LoopAuthExceptionEnum.LOGIN_NOT_EXIST_F,"Token illegal");
        UserSession userSession = new UserSession()
                .setTokenModelNow(tokenBodyModel);
        // 开启持久化执行
        if (SessionStrategy.getLoopAuthConfig().getTokenPersistence()){
            // 从内存获取loginId  后  刷新userSession存储 最后刷新当前TokenModel 生成持久化的合法会话
            userSession.gainUserSession();
            // 验证存储中是否存在
            Optional.ofNullable(userSession.getTokenModelNow())
                    .orElseThrow(() -> new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST));
            // 查看是否过期
            if (isExpire(userSession.getTokenModelNow())){
                // 注销 当前token
                getUserSession().removeToken(userSession.getTokenModelNow().getValue());
                // 删除cookie
                delCookie(SessionStrategy.getLoopAuthConfig().getTokenName());
                throw new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST_F, "The token is due");
            }
        }
        // 存储会话
        SessionStrategy.getLoopAuthContext().getStorage().set(
                "userSession",
                userSession);
        // 写入登录状态
        SessionStrategy.getLoopAuthContext().getStorage().set("isLogin", true);
    }


    /**
     * token是否过期
     * @author Sober
     * @param tokenModel token模型
     * @return boolean
     */
    private boolean isExpire(TokenModel tokenModel) {
        if (SessionStrategy.getLoopAuthConfig().getTimeOut() != -1){
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
        if (SessionStrategy.getLoopAuthConfig()
                .getAccessModes().stream()
                .anyMatch(tokenAccessMode -> tokenAccessMode == TokenAccessMode.COOKIE)){
            LoopAuthCookie cookie = new LoopAuthCookie()
                    .setName(SessionStrategy.getLoopAuthConfig().getTokenName())
                    .setValue(tokenModel.getValue());
            SessionStrategy.getLoopAuthContext().getResponse()
                    .addHeader("Set-Cookie",cookie.toCookieString());
        }

        // 如果选择了HEADER获取  则 写入Response头返回
        if (SessionStrategy.getLoopAuthConfig()
                .getAccessModes().stream()
                .anyMatch(tokenAccessMode -> tokenAccessMode == TokenAccessMode.HEADER)){
            SessionStrategy.getLoopAuthContext().getResponse().setHeader(
                    SessionStrategy.getLoopAuthConfig().getTokenName(),
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
        // 如果选择了COOKIE获取  则 删除
        if (SessionStrategy.getLoopAuthConfig()
                .getAccessModes().stream()
                .anyMatch(tokenAccessMode -> tokenAccessMode == TokenAccessMode.COOKIE)){
            LoopAuthCookie cookie = new LoopAuthCookie()
                    .setName(name)
                    .setValue("None")
                    .setMaxAge(0);
            SessionStrategy.getLoopAuthContext().getResponse()
                    .addHeader("Set-Cookie",cookie.toCookieString());
        }
    }

}