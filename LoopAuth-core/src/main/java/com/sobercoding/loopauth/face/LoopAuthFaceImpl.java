package com.sobercoding.loopauth.face;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.face.component.LoopAuthLogin;
import com.sobercoding.loopauth.face.component.LoopAuthPermission;
import com.sobercoding.loopauth.face.component.LoopAuthToken;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;
import com.sobercoding.loopauth.model.constant.LoopAuthVerifyMode;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 开放接口
 * @create: 2022/07/30 23:46
 */
public class LoopAuthFaceImpl {

    public static LoopAuthLogin LOOP_AUTH_LOGIN = LoopAuthStrategy.getLoopAuthLogin();

    public static LoopAuthToken LOOP_AUTH_TOKEN = LoopAuthStrategy.getLoopAuthToken();

    public static LoopAuthPermission LOOP_AUTH_PERMISSION = LoopAuthStrategy.getLoopAuthPermission();

    /**
     * @Method: login
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录,不设置终端区别
     * @param loginId 用户id
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public static void login(String loginId) {
        LOOP_AUTH_LOGIN.login(
                new TokenModel()
                        .setFacility("LoopAuth")
                        .setLoginId(loginId)
                        .setCreateTime(System.currentTimeMillis())
                        .setTimeOut(LoopAuthStrategy.getLoopAuthConfig().getTimeOut())
        );
    }

    /**
     * @Method: login
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录,设置终端
     * @param loginId 用户id
     * @param facility 终端类型
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public static void login(String loginId, String facility) {
        LOOP_AUTH_LOGIN.login(
                new TokenModel()
                        .setFacility(facility)
                        .setLoginId(loginId)
                        .setCreateTime(System.currentTimeMillis())
                        .setTimeOut(LoopAuthStrategy.getLoopAuthConfig().getTimeOut())
        );
    }

    /**
     * @Method: logout
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 注销当前会话账号的某个登录
     * 不填写参数则，注销当前登录
     * @param tokenModelValues 需要注销的token
     * @Return: boolean 操作是否成功
     * @Exception:
     * @Date: 2022/8/8 17:05
     */
    public static void logout(String... tokenModelValues) {
        LOOP_AUTH_LOGIN.logout(tokenModelValues);
    }


    /**
     * @Method: getTokenModel
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取当前会话的okenModel
     * @param
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public static TokenModel getTokenModel(){
        return LOOP_AUTH_LOGIN.getTokenModel();
    }

    /**
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 提取当前会话UserSession
     * @param
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public static UserSession getUserSession(){
        return LOOP_AUTH_LOGIN.getUserSession();
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
    public static void isLogin(){
        LOOP_AUTH_LOGIN.isLogin();
    }

    /**
     * @Method: loginRenew
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 登录续期
     * @param
     * @Return: java.lang.String
     * @Exception:
     * @Date: 2022/8/10 16:33
     */
    public static void loginRenew(){
        LOOP_AUTH_LOGIN.loginRenew();
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
