package com.sobercoding.loopauth.face;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthParamException;
import com.sobercoding.loopauth.face.component.LoopAuthLogin;
import com.sobercoding.loopauth.face.component.LoopAuthPermission;
import com.sobercoding.loopauth.face.component.LoopAuthToken;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;
import com.sobercoding.loopauth.model.constant.LoopAuthVerifyMode;

/**
 * 开放接口
 * @author: Sober
 */
public class LoopAuthFaceImpl {

    public static LoopAuthLogin LOOP_AUTH_LOGIN = LoopAuthStrategy.getLoopAuthLogin();

    public static LoopAuthToken LOOP_AUTH_TOKEN = LoopAuthStrategy.getLoopAuthToken();

    public static LoopAuthPermission LOOP_AUTH_PERMISSION = LoopAuthStrategy.getLoopAuthPermission();

    /**
     * 登录, 设置终端
     * @author Sober
     * @param loginId 登录id
     */
    public static void login(String loginId) {
        LoopAuthParamException.isNotEmpty(loginId, LoopAuthExceptionEnum.PARAM_IS_NULL);
        LOOP_AUTH_LOGIN.login(
                new TokenModel()
                        .setFacility("LoopAuth")
                        .setLoginId(loginId)
                        .setCreateTime(System.currentTimeMillis())
                        .setTimeOut(LoopAuthStrategy.getLoopAuthConfig().getTimeOut())
        );
    }

    /**
     * 登录, 设置终端
     * @author Sober
     * @param loginId 登录id
     * @param facility 终端类型
     */

    public static void login(String loginId, String facility) {
        LoopAuthParamException.isNotEmpty(loginId, LoopAuthExceptionEnum.PARAM_IS_NULL);
        LoopAuthParamException.isNotEmpty(facility, LoopAuthExceptionEnum.PARAM_IS_NULL);
        LOOP_AUTH_LOGIN.login(
                new TokenModel()
                        .setFacility(facility)
                        .setLoginId(loginId)
                        .setCreateTime(System.currentTimeMillis())
                        .setTimeOut(LoopAuthStrategy.getLoopAuthConfig().getTimeOut())
        );
    }

    /**
     * 注销某Token登录
     * @author Sober
     * @param tokenModelValues token列表
     */
    public static void logout(String... tokenModelValues) {
        LoopAuthParamException.isNotEmpty(tokenModelValues, LoopAuthExceptionEnum.PARAM_IS_NULL);
        LOOP_AUTH_LOGIN.logout(tokenModelValues);
    }


    /**
     * 获取当前请求的token模型
     * @author Sober
     * @return com.sobercoding.loopauth.model.TokenModel
     */
    public static TokenModel getTokenModel(){
        return LOOP_AUTH_LOGIN.getTokenModel();
    }

    /**
     * 获取当前用户所有会话
     * @author Sober
     * @return com.sobercoding.loopauth.model.UserSession
     */
    public static UserSession getUserSession(){
        return LOOP_AUTH_LOGIN.getUserSession();
    }


    /**
     * 登录校验
     * @author Sober
     */
    public static void isLogin(){
        LOOP_AUTH_LOGIN.isLogin();
    }

    /**
     * 登录续期
     * @author Sober
     */
    public static void loginRenew(){
        LOOP_AUTH_LOGIN.loginRenew();
    }

    /**
     * 角色认证
     * @author: Sober
     * @param loopAuthVerifyMode 认证方式
     * @param roles 角色列表
     */
    public static void checkByRole(LoopAuthVerifyMode loopAuthVerifyMode, String... roles) {
        LoopAuthParamException.isNotEmpty(roles, LoopAuthExceptionEnum.PARAM_IS_NULL);
        LOOP_AUTH_PERMISSION.checkByRole(loopAuthVerifyMode,roles);
    }

    /**
     * 权限认证
     * @author: Sober
     * @param loopAuthVerifyMode 认证方式
     * @param permissions 权限代码列表
     */
    public static void checkByPermission(LoopAuthVerifyMode loopAuthVerifyMode, String... permissions) {
        LOOP_AUTH_PERMISSION.checkByPermission(loopAuthVerifyMode,permissions);
    }
}
