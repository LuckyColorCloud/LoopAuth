package com.sobercoding.loopauth.face;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.face.component.LoopAuthLogin;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.constant.LoopAuthVerifyMode;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 开放接口
 * @create: 2022/07/30 23:46
 */
public class LoopAuthFaceImpl {

    public static LoopAuthLogin LOOP_AUTH_LOGIN = new LoopAuthLogin();

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
                        .setCreateTime(System.currentTimeMillis())
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
                        .setCreateTime(System.currentTimeMillis())
                        .setTimeOut(LoopAuthStrategy.getLoopAuthConfig().getTimeOut())
        );
    }

    /**
     * @Method: logout
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注销当前会话账号的某个终端登录
     * @param facilitys 终端类型 可多输入Or留空
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public static void logoutNow(String... facilitys) {
        LOOP_AUTH_LOGIN.logoutNow(facilitys);
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
    public static String getLoginId(){
        return LOOP_AUTH_LOGIN.getLoginId();
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
    public static void isLoginNow(){
        LOOP_AUTH_LOGIN.isLoginNow();
    }

    /**
     * @Method: checkByRole
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 角色认证
     * @param loopAuthVerifyMode 认证方式
     * @param roles 角色列表
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public static void checkByRole(LoopAuthVerifyMode loopAuthVerifyMode, String... roles) {
        LoopAuthStrategy.getLoopAuthPermission().checkByRole(loopAuthVerifyMode,roles);
    }

    /**
     * @Method: checkByPermission
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 权限认证
     * @param loopAuthVerifyMode 认证方式
     * @param permissions 权限代码列表
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public static void checkByPermission(LoopAuthVerifyMode loopAuthVerifyMode, String... permissions) {
        LoopAuthStrategy.getLoopAuthPermission().checkByPermission(loopAuthVerifyMode,permissions);
    }
}
