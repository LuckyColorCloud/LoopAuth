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
 * @program: LoopAuth
 * @author: Sober
 * @Description: 登录操作
 * @create: 2022/08/08 17:27
 */
public class LoopAuthLogin {

    // 可重写开始

    /**
     * @param tokenModel token模型
     * @Method: login
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录, 设置终端
     * @Return: java.lang.String 返回token
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public void login(TokenModel tokenModel) {
        // 创建会话
        UserSession userSession = creationSession(tokenModel);

        // 写入上下文
        setContext(userSession,tokenModel);

    }

    /**
     * @Method: loginRenew
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录续期
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
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
        setContext(userSession,tokenModel);
    }

    /**
     * @param tokenModelValues token列表
     * @Method: logoutNow
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注销某终端登录
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
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
     * @Method: isLoginNow
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录校验
     * @param
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public void isLogin() {
        getTokenModel();
    }

    /**
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取当前用户所有会话
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/9 23:05
     */
    public UserSession getUserSession() {
        return getUserSessionByLoginId(getTokenModel().getLoginId());
    }

    /**
     * @Method: getTokenModel
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取当前请求的token模型
     * @Return: TokenModel
     * @Exception:
     * @Date: 2022/8/11 16:42
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
     * @param tokenModel token模型
     * @Method: creationSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 创建会话，创建token写入缓存
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/9 23:18
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
     * @param loginId 用户id
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 从存储获取用户所有会话
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/9 23:05
     */
    private UserSession getUserSessionByLoginId(String loginId) {
        return new UserSession().setLoginId(loginId)
                .getUserSession();
    }

    /**
     * @Method: getBodyToken
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 从请求体获取token  通过合法认证的token但未必有效
     * @Return:
     * @Exception:
     * @Date: 2022/8/11 16:43
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
     * @Method: isExpire
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: token是否过期
     * @param
     * @Return: boolean
     * @Exception:
     * @Date: 2022/8/13 18:44
     */
    private boolean isExpire(TokenModel tokenModel) {
        if (LoopAuthStrategy.getLoopAuthConfig().getTimeOut() != -1){
            return tokenModel.getCreateTime() + tokenModel.getTimeOut() < System.currentTimeMillis();
        }
        return false;
    }

    /**
     * @Method: setContext
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 写入上下文
     * @param tokenModel
     * @Return: void
     * @Exception:
     * @Date: 2022/8/17 20:09
     */
    private void setContext(UserSession userSession, TokenModel tokenModel){
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
     * @Method: setContext
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: setCookie
     * @param name 名字
     * @Return: void
     * @Exception:
     * @Date: 2022/8/17 20:09
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
