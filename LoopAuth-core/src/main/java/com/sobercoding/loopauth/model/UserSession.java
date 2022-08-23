package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 用户会话模型
 * @author: Sober
 */
public class UserSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String loginId;

    /**
     * token列表
     */
    private Set<TokenModel> tokens;

    public UserSession setLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public String getLoginId() {
        return loginId;
    }

    public Set<TokenModel> getTokens() {
        return tokens;
    }


    /**
     * 建立会话
     * @author Sober
     * @param tokenModel token模型
     * @return com.sobercoding.loopauth.model.UserSession
     */
    public UserSession setToken(TokenModel tokenModel) {
        tokens.addAll(
                LoopAuthStrategy.loginRulesMatching.exe(
                tokens.stream().filter(item -> !item.isRemoveFlag()).collect(Collectors.toSet()),
                tokenModel)
        );
        // 登录规则检测
        return this;
    }

    /**
     * 对内存的直接操作
     * 刷新会话存储，除remove以外，所有对于会话的更新操作均需要调用setUserSession进行存储刷新
     * @author Sober
     */
    public void setUserSession(){
        // 如果已经不存在会话则删除用户所有会话存储
        if (tokens.stream().allMatch(TokenModel::isRemoveFlag)){
            // 删除会话
            remove();
        }else {
            tokens.stream().filter(TokenModel::isRemoveFlag).forEach(TokenModel::remove);
            // 过滤需要删除的会话
            tokens = tokens.stream().filter(item -> !item.isRemoveFlag()).collect(Collectors.toSet());
            Set<String> tokenValues = new HashSet<>();
            // 原子化
            AtomicLong maxExpirationTime = new AtomicLong(0);
            // 缓存时间
            long storageTimeOut = LoopAuthStrategy.getLoopAuthConfig().getStorageTimeOut();
            // 写入token对应模型
            tokens.forEach(tokenModel -> {
                // 加载缓存过期时间
                long expirationTime = storageTimeOut == 0 ?
                        tokenModel.getTimeOut() :
                        storageTimeOut;
                // 判断所以会话最晚过期时间
                if (expirationTime > maxExpirationTime.get()){
                    maxExpirationTime.set(expirationTime);
                }
                tokenModel.setTokenModel(expirationTime);
                // 组装tokens
                tokenValues.add(tokenModel.getValue());
            });
            // 写入loginId对应tokens
            LoopAuthStrategy.getLoopAuthDao()
                    .set(
                            LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix() + ":" + loginId,
                            tokenValues,
                            maxExpirationTime.get()
                    );
        }
    }

    /**
     * 对内存的直接操作
     * 获取内存中用户的所有UserSession
     * @author Sober
     * @return com.sobercoding.loopauth.model.UserSession
     */
    public UserSession gainTokenModel(){
        Set<String> tokenSet = (Set<String>) LoopAuthStrategy.getLoopAuthDao()
                .get(LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix() +
                        ":" +
                        loginId);
        Set<TokenModel> tokenModels = new HashSet<>();
        if (LoopAuthUtil.isNotEmpty(tokenSet)){
            // 过滤过期空值之后组装 Set<TokenModel>
            tokenSet.stream().filter(LoopAuthUtil::isNotEmpty).forEach(token ->
                    tokenModels.add(new TokenModel().setValue(token).gainTokenModel())
            );
        }
        this.tokens = tokenModels.stream().filter(LoopAuthUtil::isNotEmpty).collect(Collectors.toSet());
        return this;
    }

    /**
     * 对内存的直接操作，删除当前用户所有会话
     * @author Sober
     */
    public void remove(){
        LoopAuthStrategy.getLoopAuthDao()
                .remove(
                        LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix() +
                        ":" +
                        loginId
                );
        this.getTokens().forEach(TokenModel::remove);
        this.loginId = null;
        this.tokens = null;
    }



    /**
     * 删除会话token
     * @author Sober
     * @param tokenModelValues token值
     * @return com.sobercoding.loopauth.model.UserSession
     */
    public UserSession removeToken(Collection<String> tokenModelValues) {
        tokens.stream()
                .filter(tokenModel -> tokenModelValues.contains(tokenModel.getValue()))
                .forEach(tokenModel -> tokenModel.setRemoveFlag(true));
        return this;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "loginId='" + loginId + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
