package com.sobercoding.loopauth.face;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.face.component.LoopAuthLogin;
import com.sobercoding.loopauth.model.TokenModel;

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
        return LOOP_AUTH_LOGIN.getUserId();
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
    public static TokenModel getTokenNow(){
        return LOOP_AUTH_LOGIN.getTokenNow();
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
    public static boolean isLoginNow(){
        return LOOP_AUTH_LOGIN.isLoginNow();
    }

}
