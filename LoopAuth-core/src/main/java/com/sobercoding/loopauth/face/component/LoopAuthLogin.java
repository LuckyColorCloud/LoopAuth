package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;



/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 登录操作
 * @create: 2022/08/08 17:27
 */
public class LoopAuthLogin {

    // 会话操作开始

    /**
     * @param loginId     用户id
     * @param tokenModel token模型
     * @Method: login
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录, 设置终端
     * @Return: java.lang.String 返回token
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public TokenModel login(String loginId, TokenModel tokenModel) {
        // 创建会话
        creationSession(loginId, tokenModel);

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
        // 获取当前会话的userSession
        UserSession userSession = getUserSession(getLoginId());
        if (facilitys.length > 0) {
            // 注销 所选的设备
            userSession.removeTokenByFacility(facilitys)
                    .setUserSession();
        } else {
            // 通过上下文获取当前用户token 注销
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
     * @param loginId     用户id
     * @param tokenModel token模型
     * @Method: creationSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 创建会话，创建token写入缓存
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/9 23:18
     */
    public void creationSession(String loginId, TokenModel tokenModel) {
        // 生成token载入到tokenModel
        String token = LoopAuthStrategy.getLoopAuthToken()
                .createToken(
                        loginId,
                        LoopAuthStrategy.getSecretKey.apply(loginId)
                );
        // 获取存储用户的所有会话   写入当前会话   刷新会话
        getUserSession(loginId)
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
    public UserSession getUserSession(String loginId) {
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
    public TokenModel getTokenNow() {
        // 从请求体获取携带的token
        String token = getBodyToken();
        return getUserSession(getLoginId())
                .getTokens()
                .stream()
                .filter(
                        // 过滤出token值一样的TokenModel
                        tokenModel -> token.equals(tokenModel.getValue())
                ).findAny()
                // 为空则抛出异常 否则正常返回
                .orElseThrow(() -> new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST));
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
    public String getBodyToken() {
        // 从请求体获取携带的token
        String token = LoopAuthStrategy.getLoopAuthContext().getRequest().getHeader(LoopAuthStrategy.getLoopAuthConfig().getTokenName());
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
    public Object getBodyTokenInfo() {
        // 从请求体获取携带的token
        String token = getBodyToken();
        // 解析token参数
        return LoopAuthStrategy.getLoopAuthToken().getInfo(token);
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
        return (String) getBodyTokenInfo();
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
        String info = (String) getBodyTokenInfo();
        // token合法验证
        LoopAuthLoginException.isTrue(
                LoopAuthStrategy.getLoopAuthToken().verify(getTokenNow().getValue(), LoopAuthStrategy.getSecretKey.apply(info)),
                LoopAuthExceptionEnum.LOGIN_NOT_EXIST);
    }
}
