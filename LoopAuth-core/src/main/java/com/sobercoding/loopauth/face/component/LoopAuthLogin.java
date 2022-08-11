package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
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
     * @param userId     用户id
     * @param tokenModel token模型
     * @Method: login
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录, 设置终端
     * @Return: java.lang.String 返回token
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public TokenModel login(String userId, TokenModel tokenModel) {
        // 创建会话
        creationSession(userId, tokenModel);

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
        UserSession userSession = getUserSession(getUserId());
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
        getUserSession(getUserId()).remove();
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
    public void creationSession(String userId, TokenModel tokenModel) {
        // 生成token载入到tokenModel
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
     * @param userId 用户id
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 从存储获取用户所有会话
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/9 23:05
     */
    public UserSession getUserSession(String userId) {
        // 先判断userId是否存在 否则抛出异常
        LoopAuthLoginException.isTrue(
                LoopAuthStrategy.getLoopAuthDao().containsKey(userId),
                LoopAuthExceptionEnum.LOGIN_NOT_EXIST);
        return new UserSession().setUserId(userId)
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
        return getUserSession(getUserId())
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
    public Map<String, String> getBodyTokenInfo() {
        // 从请求体获取携带的token
        String token = getBodyToken();
        // 解析token参数
        Map<String, String> info = LoopAuthStrategy.getTokenBehavior().getInfo(token);
        return info;
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
    public String getUserId() {
        // token合法验证
        LoopAuthLoginException.isTrue(isLoginNow(), LoopAuthExceptionEnum.LOGIN_NOT_EXIST);
        return getBodyTokenInfo().get("userId");
    }

    /**
     * @Method: isLoginNow
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 判断当前是否登录
     * @param
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public boolean isLoginNow() {
        // 解析token参数
        Map<String, String> info = getBodyTokenInfo();
        // TODO: 2022/8/11
        // 需要处理decodeToken抛出的异常
        // TODO: 2022/8/11
        // 需要增加token对内存的鉴定，查看内存中是否存在
        // token合法验证
        return LoopAuthStrategy.getTokenBehavior().decodeToken(getBodyToken(), LoopAuthStrategy.getSecretKey.apply(info.get("userId")));
    }
}
