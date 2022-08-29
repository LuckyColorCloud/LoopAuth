package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.face.LoopAuthFaceImpl;
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

    /**
     * 当前tokenModel
     */
    private TokenModel tokenModelNow;

    public TokenModel getTokenModelNow() {
        if (LoopAuthUtil.isNotEmpty(tokenModelNow)){
            tokenModelNow = tokens.stream()
                    .filter(item -> tokenModelNow.getValue().equals(item.getValue()))
                    .findAny()
                    .orElse(null);
        }
        return tokenModelNow;
    }

    public UserSession setTokenModelNow(TokenModel tokenModelNow) {
        // 开启持久化执行
        if (LoopAuthStrategy.getLoopAuthConfig().getTokenPersistence()){
            setLoginId(gainLongId(tokenModelNow));
        }else {
            setLoginId(tokenModelNow.getLoginId());
        }
        this.tokenModelNow = tokenModelNow;
        return this;
    }

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
     *
     * @param tokenModel token模型
     * @author Sober
     */
    public UserSession setToken(TokenModel tokenModel) {
        // 登录规则检测
        Set<TokenModel> removeTokenModels = LoopAuthStrategy.loginRulesMatching.exe(tokens, tokenModel);
        // 删除不符合规则的过去token
        removeTokenModels.forEach(this::removeTokenModel);
        // 存入token
        depositTokenModel(tokenModel);
        // 更新userSession的tokens
        tokens.add(tokenModel);
        // 存入loginId
        depositUserSession();
        return this;
    }


    /**
     * 对内存的直接操作
     * 获取内存中用户的所有UserSession
     * @author Sober
     * @return com.sobercoding.loopauth.model.UserSession
     */
    public UserSession gainUserSession(){
        tokens = (Set<TokenModel>) LoopAuthStrategy.getLoopAuthDao()
                .get(LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix() +
                        ":" +
                        loginId);
        if (Optional.ofNullable(tokens).isPresent()){
            long timeMillis = System.currentTimeMillis();
            tokens = tokens.stream()
                    .filter(item -> (item.getCreateTime() + item.getTimeOut()) > timeMillis)
                    .collect(Collectors.toSet());
        }else {
            tokens = new HashSet<>();
        }
        // 加载一下当前会话
        getTokenModelNow();
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
        this.getTokens().forEach(this::removeTokenModel);
        this.loginId = null;
        this.tokens = null;
    }


    /**
     * 删除会话token
     *
     * @param tokenModelValues token值
     * @author Sober
     */
    public UserSession removeToken(String... tokenModelValues) {
        // 不存在则不操作删除
        if (tokenModelValues.length <= 0){
            return this;
        }
        Set<String> tokenSet = new HashSet<>(Arrays.asList(tokenModelValues));
        // 找到符合的tokenModel删除
        tokens.stream()
                .filter(tokenModel -> tokenSet.contains(tokenModel.getValue()))
                .forEach(this::removeTokenModel);
        // 更新userSession的tokens
        tokens = tokens.stream()
                .filter(tokenModel -> !tokenSet.contains(tokenModel.getValue()))
                .collect(Collectors.toSet());
        // 存入loginId
        depositUserSession();
        return this;
    }

    /**
     * 对内存的直接操作 刷新/存储LoginId
     * @author Sober
     */
    private void depositUserSession(){
        // 如果已经不存在会话则删除用户所有会话存储
        if (tokens.size() <= 0){
            // 删除会话
            remove();
        }else {
            // 原子化
            AtomicLong maxExpirationTime = new AtomicLong(0);
            tokens.forEach(tokenModel -> {
                // 加载过期时间
                long expirationTime = tokenModel.getTimeOut() + tokenModel.getCreateTime() - System.currentTimeMillis();
                // 判断所以会话最晚过期时间
                if (expirationTime > maxExpirationTime.get()){
                    maxExpirationTime.set(expirationTime);
                }
            });
            // 写入loginId对应tokens
            LoopAuthStrategy.getLoopAuthDao()
                    .set(
                            LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix() + ":" + loginId,
                            tokens,
                            maxExpirationTime.get()
                    );
        }
    }

    /**
     * 对内存的直接操作
     * 刷新/存储Token
     */
    private void depositTokenModel(TokenModel tokenModel){
        LoopAuthStrategy.getLoopAuthDao()
                .set(
                        LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() + ":" + tokenModel.getValue(),
                        loginId,
                        tokenModel.getTimeOut()
                );
    }

    /**
     * 对内存的直接操作，删除Token
     * @author Sober
     */
    private void removeTokenModel(TokenModel tokenModel){
        LoopAuthStrategy.getLoopAuthDao()
                .remove(LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() +
                        ":" +
                        tokenModel.getValue());
    }

    /**
     * 对内存的直接操作
     * 获取内存中当前token的loginId
     * @author Sober
     * @return long
     */
    private String gainLongId(TokenModel tokenModel){
        return (String) LoopAuthStrategy.getLoopAuthDao()
                .get(LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() +
                        ":" +
                        tokenModel.getValue());
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "loginId='" + loginId + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
