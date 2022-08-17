package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;
import com.sobercoding.loopauth.model.constant.TokenAccessMode;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.HashMap;
import java.util.Map;


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
    public TokenModel login(TokenModel tokenModel) {
        // 创建会话
        creationSession(tokenModel);

        // 写入上下文
        setContext(tokenModel);
        // 返回token模型
        return tokenModel;
    }

    /**
     * @param facilitys 终端类型 可多输入
     * @Method: logout
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注销某终端登录
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public void logoutNow(String... facilitys) {
        // TODO: 2022/8/18
//        cookie如果有也要删除
        // 获取当前会话的userSession
        UserSession userSession = getUserSession(getLoginId());
        if (facilitys.length > 0) {
            // 注销 所选的设备
            userSession.removeTokenByFacility(facilitys)
                    .setUserSession();
        } else {
            // 注销 当前token
            userSession.removeToken(getTokenNow().getValue())
                    .setUserSession();
        }
    }

    /**
     * @Method: logout
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注销当前会话用户的所有登录状态
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public void logoutNowAll() {
        // 获取会话    删除所有token
        getUserSession(getLoginId()).remove();
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
    public String getLoginId() {
        // 登录验证
        isLoginNow();
        return getBodyTokenInfo().get("loginId");
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
    public void isLoginNow() {
        // 解析token参数
        String loginId = getBodyTokenInfo().get("loginId");
        // token合法验证
        LoopAuthLoginException.isTrue(
                LoopAuthStrategy.getLoopAuthToken().verify(getTokenNow().getValue(), LoopAuthStrategy.getSecretKey.apply(loginId)),
                LoopAuthExceptionEnum.LOGIN_NOT_EXIST);
        // 登录有效期认证
        LoopAuthLoginException.isTrue(
                !isExpire(),
                LoopAuthExceptionEnum.LOGIN_EXPIRE);
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
    private void creationSession(TokenModel tokenModel) {
        // 生成token载入到tokenModel
        Map<String,String> info = new HashMap<>(4);
        info.put("loginId",tokenModel.getLoginId());
        info.put("createTime",String.valueOf(tokenModel.getCreateTime()));
        info.put("timeOut",String.valueOf(tokenModel.getTimeOut()));
        String token = LoopAuthStrategy.getLoopAuthToken()
                .createToken(
                        info,
                        LoopAuthStrategy.getSecretKey.apply(tokenModel.getLoginId())
                );
        // 获取存储用户的所有会话   写入当前会话   刷新会话
        getUserSession(tokenModel.getLoginId())
                .setToken(tokenModel.setValue(token))
                .setUserSession();
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
    private UserSession getUserSession(String loginId) {
        return new UserSession().setUserId(loginId)
                .getUserSession();
    }


    /**
     * @Method: getTokenNow
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取当前请求的token模型
     * @Return: TokenModel
     * @Exception:
     * @Date: 2022/8/11 16:42
     */
    private TokenModel getTokenNow() {
        // 有限从会话存储获取
        TokenModel strategyTokenModel = (TokenModel) LoopAuthStrategy.getLoopAuthContext().getStorage()
                .get("tokenModel");
        if (LoopAuthUtil.isEmpty(strategyTokenModel)){
            // 从请求体获取携带的token
            String token = getBodyToken();
            TokenModel userSessionTokenModel = getUserSession(getBodyTokenInfo().get("loginId"))
                    .getTokens()
                    .stream()
                    .filter(
                            // 过滤出token值一样的TokenModel
                            tokenModel -> token.equals(tokenModel.getValue())
                    ).findAny()
                    // 为空则抛出异常 否则正常返回
                    .orElseThrow(() -> new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST));
            // 写入当前会话Context存储器
            LoopAuthStrategy.getLoopAuthContext().getStorage().set("tokenModel",userSessionTokenModel);
            return userSessionTokenModel;
        }
        return strategyTokenModel;
    }

    /**
     * @Method: getBodyToken
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 从请求体获取token
     * 此token不被信任，需要验证
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
        return token;
    }


    /**
     * @Method: getBodyToken
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 从请求体获取token携带的参数
     * 此token不被信任，需要验证
     * @Return:
     * @Exception:
     * @Date: 2022/8/11 16:43
     */
    private Map<String,String> getBodyTokenInfo() {
        // 从请求体获取携带的token
        String token = getBodyToken();
        // 解析token参数
        Map<String,String> infoMap = (Map<String, String>) LoopAuthStrategy.getLoopAuthToken().getInfo(token);
        LoopAuthLoginException.isEmpty(infoMap,LoopAuthExceptionEnum.LOGIN_NOT_EXIST);
        return infoMap;
    }

    /**
     * @Method: isExpire
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: token有效期认证
     * @param
     * @Return: boolean
     * @Exception:
     * @Date: 2022/8/13 18:44
     */
    private boolean isExpire() {
        TokenModel tokenModel = getTokenNow();
        if (LoopAuthStrategy.getLoopAuthConfig().getTimeOut() != -1){
            if (tokenModel.getCreateTime() + tokenModel.getTimeOut() < System.currentTimeMillis()){
                // 到期删除当前token
                getUserSession(tokenModel.getLoginId())
                        .removeToken(tokenModel.getValue())
                        .setUserSession();
                return true;
            }
        }
        // token续期
        if (LoopAuthStrategy.getLoopAuthConfig().getRenew()){
            getUserSession(tokenModel.getLoginId())
                    .renewalToken(tokenModel.getValue())
                    .setUserSession();
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
    private void setContext(TokenModel tokenModel){
        // 如果选择了COOKIE获取  则 写入Response的Cookie存储
        if (LoopAuthStrategy.getLoopAuthConfig()
                .getAccessModes().stream()
                .anyMatch(tokenAccessMode -> tokenAccessMode == TokenAccessMode.COOKIE)){
            LoopAuthStrategy.getLoopAuthContext().getResponse().addCookie(
                    LoopAuthStrategy.getLoopAuthConfig().getTokenName(),
                    tokenModel.getValue()
            );
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

        // 写入内部使用的会话存储,
        LoopAuthStrategy.getLoopAuthContext().getStorage()
                .set("tokenModel", tokenModel);
    }


}
