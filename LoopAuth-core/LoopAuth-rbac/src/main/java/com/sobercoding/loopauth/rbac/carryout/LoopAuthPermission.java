package com.sobercoding.loopauth.rbac.carryout;

import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.model.LoopAuthVerifyMode;
import com.sobercoding.loopauth.rbac.RbacStrategy;
import com.sobercoding.loopauth.util.AuthUtil;

/**
 * @author: Sober
 */
public class LoopAuthPermission {


    /**
     * 角色认证
     * @author: Sober
     * @param loopAuthVerifyMode 认证方式
     * @param roles 角色列表
     */
    public void checkByRole(LoopAuthVerifyMode loopAuthVerifyMode, String... roles) {
        String loginId = RbacStrategy.getLoginId.get();
        //TODO  缺少登入类型 暂做扩展
        String loginType = "";
        boolean pass;
        if (loopAuthVerifyMode == LoopAuthVerifyMode.AND) {
            pass = AuthUtil.checkAnd(RbacStrategy.getPermissionInterface().getRoleSet(loginId, loginType), roles);
        } else if (loopAuthVerifyMode == LoopAuthVerifyMode.OR) {
            pass = AuthUtil.checkOr(RbacStrategy.getPermissionInterface().getRoleSet(loginId, loginType), roles);
        } else {
            pass = AuthUtil.checkNon(RbacStrategy.getPermissionInterface().getRoleSet(loginId, loginType), roles);
        }
        if (!pass){
            throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
        }
    }

    /**
     * 权限认证
     * @author: Sober
     * @param loopAuthVerifyMode 认证方式
     * @param permissions 权限代码列表
     */
    public void checkByPermission(LoopAuthVerifyMode loopAuthVerifyMode, String... permissions) {
        String loginId = RbacStrategy.getLoginId.get();
        //TODO  缺少登入类型 暂做扩展
        String loginType = "";
        boolean pass;
        if (loopAuthVerifyMode == LoopAuthVerifyMode.AND) {
            pass = AuthUtil.checkAnd(RbacStrategy.getPermissionInterface().getPermissionSet(loginId, loginType), permissions);
        } else if (loopAuthVerifyMode == LoopAuthVerifyMode.OR) {
            pass = AuthUtil.checkOr(RbacStrategy.getPermissionInterface().getPermissionSet(loginId, loginType), permissions);
        } else {
            pass = AuthUtil.checkNon(RbacStrategy.getPermissionInterface().getPermissionSet(loginId, loginType), permissions);
        }
        if (!pass){
            throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
        }
    }

}
