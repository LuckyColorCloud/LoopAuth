package com.sobercoding.loopauth.util;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.annotation.LoopAuthMode;
import com.sobercoding.loopauth.annotation.LoopAuthPermission;
import com.sobercoding.loopauth.annotation.LoopAuthRole;
import com.sobercoding.loopauth.annotation.LoopAutoCheckLogin;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.NotRoleException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author Yun
 */
public class PermissionUtil {
    private PermissionUtil() {
    }

    /**
     * 获取注解 鉴权
     */
    public static Consumer<Method> checkMethodAnnotation = (method) -> {

        // 先校验 Method 所属 Class 上的注解
        PermissionUtil.checkElementAnnotation.accept(method.getDeclaringClass());

        // 再校验 Method 上的注解
        PermissionUtil.checkElementAnnotation.accept(method);
    };


    /**
     * 逐个匹配注解 解析
     */
    public static Consumer<AnnotatedElement> checkElementAnnotation = (target) -> {

        LoopAutoCheckLogin checkLogin = (LoopAutoCheckLogin) PermissionUtil.getAnnotation.apply(target, LoopAutoCheckLogin.class);
        if (checkLogin != null) {
            PermissionUtil.checkByAnnotation(checkLogin);
        }

        LoopAuthRole checkRole = (LoopAuthRole) PermissionUtil.getAnnotation.apply(target, LoopAuthRole.class);
        if (checkRole != null) {
            PermissionUtil.checkByAnnotation(checkRole);
        }

        LoopAuthPermission checkPermission = (LoopAuthPermission) PermissionUtil.getAnnotation.apply(target, LoopAuthPermission.class);
        if (checkPermission != null) {
            PermissionUtil.checkByAnnotation(checkPermission);
        }

    };

    /**
     * 登录认证
     * @param loopAutoCheckLogin
     */
    private static void checkByAnnotation(LoopAutoCheckLogin loopAutoCheckLogin) {
            //TODO 调用 用户端接口  检查是否登入  checkLogin();
    }

    /**
     * 权限认证
     * @param loopAuthPermission
     */
    private static void checkByAnnotation(LoopAuthPermission loopAuthPermission) {
        String[] roleArray = loopAuthPermission.value();
        //TODO 获取USERID
        String loginId = "";
        String loginType = "";
        if (loopAuthPermission.mode() == LoopAuthMode.AND) {
            checkAnd(roleArray, LoopAuthStrategy.getPermissionInterface().getPermissionSet(loginId, loginType));
        } else if (loopAuthPermission.mode() == LoopAuthMode.OR) {
            checkOr(roleArray,LoopAuthStrategy.getPermissionInterface().getPermissionSet(loginId, loginType));
        } else {
            checkNon(roleArray,LoopAuthStrategy.getPermissionInterface().getPermissionSet(loginId, loginType));
        }
    }

    /**
     * 角色认证
     * @param loopAuthRole
     */
    private static void checkByAnnotation(LoopAuthRole loopAuthRole) {
        String[] roleArray = loopAuthRole.value();
        //TODO 获取USERID
        String loginId = "";
        String loginType = "";
        if (loopAuthRole.mode() == LoopAuthMode.AND) {
            checkAnd(roleArray, LoopAuthStrategy.getPermissionInterface().getRoleSet(loginId, loginType));
        } else if (loopAuthRole.mode() == LoopAuthMode.OR) {
            checkOr(roleArray,LoopAuthStrategy.getPermissionInterface().getRoleSet(loginId, loginType));
        } else {
            checkNon(roleArray,LoopAuthStrategy.getPermissionInterface().getRoleSet(loginId, loginType));
        }
    }

    private static void checkNon(String[] roleSet,Set<String> roles) {
        for (String role : roleSet) {
            if(hasElement(roles, role)) {
                throw new NotRoleException(LoopAuthExceptionEnum.NO_PERMISSION);
            }
        }
    }

    private static void checkOr(String[] roleSet,Set<String> roles) {
        for (String role : roleSet) {
            if(hasElement(roles, role)) {
                return;
            }
        }
        throw new NotRoleException(LoopAuthExceptionEnum.NO_PERMISSION);
    }

    private static void checkAnd(String[] roleSet,Set<String> roles) {
        for (String role : roleSet) {
            if(!hasElement(roles, role)) {
                throw new NotRoleException(LoopAuthExceptionEnum.NO_ROLE.setMsg(role));
            }
        }
    }

    /**
     * 匹配元素是否存在
     */
    private static boolean hasElement(Set<String> roleSet, String role) {
        // 空集合直接返回false
        if(LoopAuthUtil.isEmpty(roleSet)) {
            return false;
        }
        // 先尝试一下简单匹配，如果可以匹配成功则无需继续模糊匹配
        if (roleSet.contains(role)) {
            return true;
        }
        // 开始模糊匹配
        for (String path : roleSet) {
            if(LoopAuthUtil.fuzzyMatching(path, role)) {
                return true;
            }
        }
        return false;
    }




    /**
     * 从元素上获取注解
     */
    @SuppressWarnings("Convert2MethodRef")
    public static BiFunction<AnnotatedElement, Class<? extends Annotation>, Annotation> getAnnotation = (element, annotationClass) -> {
        // 默认使用jdk的注解处理器
        return element.getAnnotation(annotationClass);
    };



    public static boolean matchPaths(Collection<String> paths, ServletRequest request) {
        if (request == null || paths.isEmpty()) {
            return false;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getServletPath();
        for (String temp : paths) {
            if (LoopAuthUtil.fuzzyMatching(temp, path)) {
                return true;
            }
        }
        return false;
    }
}
