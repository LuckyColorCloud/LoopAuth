package com.sobercoding.loopauth;


import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.context.LoopAuthContextDefaultImpl;
import com.sobercoding.loopauth.dao.LoopAuthDao;
import com.sobercoding.loopauth.dao.LoopAuthDaoImpl;
import com.sobercoding.loopauth.face.component.LoopAuthLogin;
import com.sobercoding.loopauth.face.component.LoopAuthPermission;
import com.sobercoding.loopauth.face.component.LoopAuthToken;
import com.sobercoding.loopauth.function.LrFunction;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.permission.PermissionInterface;
import com.sobercoding.loopauth.permission.PermissionInterfaceDefImpl;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: LoopAuth  Bean管理器
 * @create: 2022/07/20 19:43
 */

public class LoopAuthStrategy {

    /**
     * 配置文件
     */
    private volatile static LoopAuthConfig loopAuthConfig;

    public static void setLoopAuthConfig(LoopAuthConfig loopAuthConfig) {
        LoopAuthStrategy.loopAuthConfig = loopAuthConfig;
    }

    public static LoopAuthConfig getLoopAuthConfig() {
        if (LoopAuthStrategy.loopAuthConfig == null){
            synchronized(LoopAuthStrategy.class){
                if (LoopAuthStrategy.loopAuthConfig == null){
                    LoopAuthStrategy.loopAuthConfig = new LoopAuthConfig();
                }
            }
        }
        return LoopAuthStrategy.loopAuthConfig;
    }

    /**
     * Token策略
     */
    private volatile static LoopAuthToken loopAuthToken;

    public static void setLoopAuthToken(LoopAuthToken loopAuthToken) {
        LoopAuthStrategy.loopAuthToken = loopAuthToken;
    }

    public static LoopAuthToken getLoopAuthToken() {
        if (LoopAuthStrategy.loopAuthToken == null){
            synchronized(LoopAuthStrategy.class){
                if (LoopAuthStrategy.loopAuthToken == null){
                    LoopAuthStrategy.loopAuthToken = new LoopAuthToken();
                }
            }
        }
        return LoopAuthStrategy.loopAuthToken;
    }

    /**
     * 验证权限策略
     */
    private volatile static LoopAuthPermission loopAuthPermission;

    public static void setLoopAuthPermission(LoopAuthPermission loopAuthPermission) {
        LoopAuthStrategy.loopAuthPermission = loopAuthPermission;
    }

    public static LoopAuthPermission getLoopAuthPermission() {
        if (LoopAuthStrategy.loopAuthPermission == null){
            synchronized(LoopAuthStrategy.class){
                if (LoopAuthStrategy.loopAuthPermission == null){
                    LoopAuthStrategy.loopAuthPermission = new LoopAuthPermission();
                }
            }
        }
        return LoopAuthStrategy.loopAuthPermission;
    }


    private volatile static LoopAuthLogin loopAuthLogin;

    public static void setLoopAuthLogin(LoopAuthLogin loopAuthLogin) {
        LoopAuthStrategy.loopAuthLogin = loopAuthLogin;
    }

    public static LoopAuthLogin getLoopAuthLogin() {
        if (LoopAuthStrategy.loopAuthLogin == null){
            synchronized(LoopAuthStrategy.class){
                if (LoopAuthStrategy.loopAuthLogin == null){
                    LoopAuthStrategy.loopAuthLogin = new LoopAuthLogin();
                }
            }
        }
        return LoopAuthStrategy.loopAuthLogin;
    }

    /**
     * 会话缓存操作
     */
    private volatile static LoopAuthDao loopAuthDao;

    public static void setLoopAuthDao(LoopAuthDao loopAuthDao) {
        LoopAuthStrategy.loopAuthDao = loopAuthDao;
    }

    public static LoopAuthDao getLoopAuthDao() {
        if (LoopAuthStrategy.loopAuthDao == null){
            synchronized(LoopAuthStrategy.class){
                if (LoopAuthStrategy.loopAuthDao == null){
                    LoopAuthStrategy.loopAuthDao = new LoopAuthDaoImpl();
                }
            }
        }
        return LoopAuthStrategy.loopAuthDao;
    }

    /**
     * 上下文Context Bean
     */
    private volatile static LoopAuthContext loopAuthContext;
    public static void setLoopAuthContext(LoopAuthContext loopAuthContext) {
        LoopAuthStrategy.loopAuthContext = loopAuthContext;
    }
    public static LoopAuthContext getLoopAuthContext() {
        if (loopAuthContext == null) {
            synchronized (LoopAuthStrategy.class) {
                if (loopAuthContext == null) {
                    setLoopAuthContext(new LoopAuthContextDefaultImpl());
                }
            }
        }
        return loopAuthContext;
    }


    /**
     * 权限认证 Bean 获取角色/权限代码
     */
    private volatile static PermissionInterface permissionInterface;
    public static void setPermissionInterface(PermissionInterface permissionInterface) {
        LoopAuthStrategy.permissionInterface = permissionInterface;
    }
    public static PermissionInterface getPermissionInterface() {
        if (permissionInterface == null) {
            synchronized (LoopAuthStrategy.class) {
                if (permissionInterface == null) {
                    setPermissionInterface(new PermissionInterfaceDefImpl());
                }
            }
        }
        return permissionInterface;
    }


    /**
     * @Method:
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取盐默认方法
     * @param: userId 用户id
     * @Return:
     * @Exception: 
     * @Date: 2022/8/9 15:21
     */
    public static Function<String,String> getSecretKey = userId -> LoopAuthStrategy
            .getLoopAuthConfig().getSecretKey();

    /**
     * @Method:
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 默认登录规则处理
     * @param: userId 用户id
     * @param: tokenModels 用户当前的token集合
     * @Return:
     * @Exception:
     * @Date: 2022/8/9 15:21
     */
    public static LrFunction<List<TokenModel>, TokenModel> loginRulesMatching = (tokenModels, tokenModel) -> {
        // 开启token共生
        if (LoopAuthStrategy.getLoopAuthConfig().getMutualism()){
            // 同端互斥开启  删除相同端的登录
            if (LoopAuthStrategy.getLoopAuthConfig().getExclusion()){
                tokenModels.remove(
                        tokenModels.stream()
                                .filter(item -> item.getFacility().equals(tokenModel.getFacility()))
                                .findAny()
                                .orElse(new TokenModel())
                );
            }
            // 登录数量超出限制  删除较早的会话
            int maxLoginCount = LoopAuthStrategy.getLoopAuthConfig().getMaxLoginCount();
            if (maxLoginCount != -1 && tokenModels.size() >= maxLoginCount){
                tokenModels = tokenModels.stream()
                        .sorted(Comparator.comparing(TokenModel::getCreateTime))
                        .collect(Collectors.toList());
                tokenModels.removeAll(
                        tokenModels.stream()
                                .limit(tokenModels.size() - maxLoginCount + 1)
                                .collect(Collectors.toList())
                );
            }
        }else {
            // 未开启token共生  清除所有会话
            tokenModels.clear();
        }
        // 添加新的会话
        tokenModels.add(tokenModel);
        return tokenModels;
    };


}
