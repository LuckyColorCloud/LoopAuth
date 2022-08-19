package com.sobercoding.loopauth.face.component;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.model.constant.LoopAuthVerifyMode;
import com.sobercoding.loopauth.face.LoopAuthFaceImpl;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Set;

/**
 * @program: LoopAuthPermission
 * @author: Sober
 * @Description:
 * @create: 2022/08/16 02:33
 */
public class LoopAuthPermission {


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
    public void checkByRole(LoopAuthVerifyMode loopAuthVerifyMode, String... roles) {
        String loginId = LoopAuthFaceImpl.getTokenModel().getLoginId();
        //TODO  缺少登入类型 暂做扩展
        String loginType = "";
        if (loopAuthVerifyMode == LoopAuthVerifyMode.AND) {
            checkAnd(LoopAuthStrategy.getPermissionInterface().getRoleSet(loginId, loginType), roles);
        } else if (loopAuthVerifyMode == LoopAuthVerifyMode.OR) {
            checkOr(LoopAuthStrategy.getPermissionInterface().getRoleSet(loginId, loginType), roles);
        } else {
            checkNon(LoopAuthStrategy.getPermissionInterface().getRoleSet(loginId, loginType), roles);
        }
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
    public void checkByPermission(LoopAuthVerifyMode loopAuthVerifyMode, String... permissions) {
        String loginId = LoopAuthFaceImpl.getTokenModel().getLoginId();
        //TODO  缺少登入类型 暂做扩展
        String loginType = "";
        if (loopAuthVerifyMode == LoopAuthVerifyMode.AND) {
            checkAnd(LoopAuthStrategy.getPermissionInterface().getPermissionSet(loginId, loginType), permissions);
        } else if (loopAuthVerifyMode == LoopAuthVerifyMode.OR) {
            checkOr(LoopAuthStrategy.getPermissionInterface().getPermissionSet(loginId, loginType), permissions);
        } else {
            checkNon(LoopAuthStrategy.getPermissionInterface().getPermissionSet(loginId, loginType), permissions);
        }
    }

    private static void checkNon(Set<String> roleSet, String... roles) {
        for (String role : roles) {
            if (hasElement(roleSet, role)) {
                throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
            }
        }
    }

    private static void checkOr(Set<String> roleSet, String... roles) {
        for (String role : roles) {
            if (hasElement(roleSet, role)) {
                return;
            }
        }
        throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
    }

    private static void checkAnd(Set<String> roleSet, String... roles) {
        for (String role : roles) {
            if (!hasElement(roleSet, role)) {
                throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
            }
        }
    }

    /**
     * 匹配元素是否存在
     */
    private static boolean hasElement(Set<String> roleSet, String role) {
        // 空集合直接返回false
        if (LoopAuthUtil.isEmpty(roleSet)) {
            return false;
        }
        // 先尝试一下简单匹配，如果可以匹配成功则无需继续模糊匹配
        if (roleSet.contains(role)) {
            return true;
        }
        // 开始模糊匹配
        for (String path : roleSet) {
            if (LoopAuthUtil.fuzzyMatching(path, role)) {
                return true;
            }
        }
        return false;
    }
}
