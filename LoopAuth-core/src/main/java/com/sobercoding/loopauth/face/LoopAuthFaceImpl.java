package com.sobercoding.loopauth.face;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.face.component.LoopAuthLogin;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;
import com.sobercoding.loopauth.util.JsonUtil;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 开放接口
 * @create: 2022/07/30 23:46
 */
public class LoopAuthFaceImpl {

    private static final LoopAuthLogin LOOP_AUTH_LOGIN = new LoopAuthLogin();

    /**
     * @Method: login
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录,不设置终端区别
     * @param userId 用户id
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public static TokenModel login(String userId) {
        return LOOP_AUTH_LOGIN.login(
                userId,
                new TokenModel()
                        .setFacility("LoopAuth"))
                        .setCreateTime(System.nanoTime())
                        .setTimeOut(LoopAuthStrategy.getLoopAuthConfig().getTimeOut()
        );
    }

    /**
     * @Method: login
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录,设置终端
     * @param userId 用户id
     * @param facility 终端类型
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public static TokenModel login(String userId, String facility) {
        return LOOP_AUTH_LOGIN.login(
                userId,
                new TokenModel()
                        .setFacility(facility)
                        .setCreateTime(System.nanoTime())
                        .setTimeOut(LoopAuthStrategy.getLoopAuthConfig().getTimeOut())
        );
    }

    /**
     * @Method: logout
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注销某账号的某个终端登录
     * @param userId 用户id
     * @param facilitys 终端类型 可多输入
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public static boolean logout(String userId,String... facilitys) {
        return LOOP_AUTH_LOGIN.logout(userId,facilitys);
    }

    /**
     * @Method: logout
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注销当前会话
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public static boolean logout() {
        return LOOP_AUTH_LOGIN.logout(getUserId());
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
    public static boolean logoutAll(String userId) {
        return LOOP_AUTH_LOGIN.logoutAll(userId);
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
    public static UserSession getUserSessionAllByUserId(String userId) {
        return LoopAuthStrategy.getLoopAuthDao().getUserSession(userId);
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
    public static UserSession getUserSessionAll() {
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
    public static UserSession getUserSessionNow() {
        UserSession userSession = getUserSessionAll();
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
    public static String getUserId(){
        String token = getTokenNow();
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
    public static String getTokenNow(){
        return LoopAuthStrategy.getLoopAuthContext()
                .getRequest()
                .getHeader(
                        LoopAuthStrategy.getLoopAuthConfig().getTokenName()
                );
    }

    /**
     * @Method: isLoginNow
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 判断当前是否登录，否则直接抛出异常
     * @param
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public static void isLoginNow(){
        LoopAuthLoginException.isLegality(getTokenNow());
    }

}
