package com.sobercoding.loopauth.face;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.face.component.LoopAuthLogin;
import com.sobercoding.loopauth.model.UserSession;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
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
    public static String login(String userId) {
        return LOOP_AUTH_LOGIN.login(userId,"DEFAULT");
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
    public static String login(String userId,String facility) {
        return LOOP_AUTH_LOGIN.login(userId,facility);
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
    public static boolean logout(String userId,String... facilitys) {
        return LOOP_AUTH_LOGIN.logout(userId,facilitys);
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
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取当前用户所有会话
     * @param userId 用户id
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 23:14
     */
    public static UserSession getUserSession(String userId) {
        return LoopAuthStrategy.getLoopAuthDao().getUserSession(userId);
    }
}
