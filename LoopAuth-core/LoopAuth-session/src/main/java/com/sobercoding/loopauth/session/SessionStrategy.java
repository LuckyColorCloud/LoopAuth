package com.sobercoding.loopauth.session;


import com.sobercoding.loopauth.function.LrFunction;
import com.sobercoding.loopauth.session.config.SessionConfig;
import com.sobercoding.loopauth.session.context.LoopAuthContext;
import com.sobercoding.loopauth.session.context.LoopAuthContextDefaultImpl;
import com.sobercoding.loopauth.session.dao.LoopAuthDao;
import com.sobercoding.loopauth.session.dao.LoopAuthDaoImpl;
import com.sobercoding.loopauth.session.face.component.LoopAuthLogin;
import com.sobercoding.loopauth.session.face.component.LoopAuthToken;
import com.sobercoding.loopauth.session.model.TokenModel;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * LoopAuth  Bean管理器
 * @author: Sober
 */

public class SessionStrategy {

    /**
     * 会话配置文件
     */
    private volatile static SessionConfig sessionConfig;

    public static void setLoopAuthConfig(SessionConfig sessionConfig) {
        SessionStrategy.sessionConfig = sessionConfig;
    }

    public static SessionConfig getLoopAuthConfig() {
        if (SessionStrategy.sessionConfig == null){
            synchronized(SessionStrategy.class){
                if (SessionStrategy.sessionConfig == null){
                    setLoopAuthConfig(new SessionConfig());
                }
            }
        }
        return SessionStrategy.sessionConfig;
    }

    /**
     * Token策略
     */
    private volatile static LoopAuthToken loopAuthToken;

    public static void setLoopAuthToken(LoopAuthToken loopAuthToken) {
        SessionStrategy.loopAuthToken = loopAuthToken;
    }

    public static LoopAuthToken getLoopAuthToken() {
        if (SessionStrategy.loopAuthToken == null){
            synchronized(SessionStrategy.class){
                if (SessionStrategy.loopAuthToken == null){
                    setLoopAuthToken(new LoopAuthToken());
                }
            }
        }
        return SessionStrategy.loopAuthToken;
    }


    private volatile static LoopAuthLogin loopAuthLogin;

    public static void setLoopAuthLogin(LoopAuthLogin loopAuthLogin) {
        SessionStrategy.loopAuthLogin = loopAuthLogin;
    }

    public static LoopAuthLogin getLoopAuthLogin() {
        if (SessionStrategy.loopAuthLogin == null){
            synchronized(SessionStrategy.class){
                if (SessionStrategy.loopAuthLogin == null){
                    setLoopAuthLogin(new LoopAuthLogin());
                }
            }
        }
        return SessionStrategy.loopAuthLogin;
    }

    /**
     * 会话缓存操作
     */
    private volatile static LoopAuthDao loopAuthDao;

    public static void setLoopAuthDao(LoopAuthDao loopAuthDao) {
        SessionStrategy.loopAuthDao = loopAuthDao;
    }

    public static LoopAuthDao getLoopAuthDao() {
        if (SessionStrategy.loopAuthDao == null){
            synchronized(SessionStrategy.class){
                if (SessionStrategy.loopAuthDao == null){
                    setLoopAuthDao(new LoopAuthDaoImpl());
                }
            }
        }
        return SessionStrategy.loopAuthDao;
    }

    /**
     * 上下文Context Bean
     */
    private volatile static LoopAuthContext loopAuthContext;
    public static void setLoopAuthContext(LoopAuthContext loopAuthContext) {
        SessionStrategy.loopAuthContext = loopAuthContext;
    }
    public static LoopAuthContext getLoopAuthContext() {
        if (loopAuthContext == null) {
            synchronized (SessionStrategy.class) {
                if (loopAuthContext == null) {
                    setLoopAuthContext(new LoopAuthContextDefaultImpl());
                }
            }
        }
        return loopAuthContext;
    }


    /**
     * 获取盐默认方法
     */
    public static Function<String,String> getSecretKey = userId -> SessionStrategy
            .getLoopAuthConfig().getSecretKey();

    /**
     * 默认登录规则处理
     * 新的tokenModel直接加入tokenModels返回需要删除的列表
     */
    public static LrFunction<Set<TokenModel>, TokenModel> loginRulesMatching = (tokenModels, tokenModel) -> {
        if (tokenModels.contains(tokenModel)){
            return new HashSet<>();
        }
        Set<TokenModel> removeTokenModels = new HashSet<>();
        // 开启token共生
        if (SessionStrategy.getLoopAuthConfig().getMutualism()){
            // 同端互斥开启  删除相同端的登录
            if (SessionStrategy.getLoopAuthConfig().getExclusion()){
                // 查看是否有同端口
                Optional<TokenModel> optionalTokenModel = tokenModels.stream()
                        .filter(item -> item.getFacility().equals(tokenModel.getFacility()))
                        .findAny();
                // 对tokenModels,removeTokenModels操作
                if (optionalTokenModel.isPresent()){
                    tokenModels.remove(optionalTokenModel.get());
                    removeTokenModels.add(optionalTokenModel.get());
                }
            }
            // 登录数量超出限制  删除较早的会话
            int maxLoginCount = SessionStrategy.getLoopAuthConfig().getMaxLoginCount();
            if (maxLoginCount != -1 && tokenModels.size() >= maxLoginCount){
                List<TokenModel> tokenModelList = new ArrayList<>(tokenModels);
                // 排序
                tokenModelList = tokenModelList.stream()
                        .sorted(Comparator.comparing(TokenModel::getCreateTime))
                        .collect(Collectors.toList());
                // 获得早期会话
                tokenModelList = tokenModelList.stream()
                        .limit(tokenModels.size() - maxLoginCount + 1)
                        .collect(Collectors.toList());
                // 对tokenModels,removeTokenModels操作
                tokenModelList.forEach(tokenModels::remove);
                removeTokenModels.addAll(tokenModelList);
            }
        }else {
            // 未开启token共生  清除所有会话
            removeTokenModels.addAll(tokenModels);
        }
        // 添加新的会话
        tokenModels.add(tokenModel);
        return removeTokenModels;
    };


}
