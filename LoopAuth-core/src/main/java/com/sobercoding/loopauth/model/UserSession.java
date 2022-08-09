package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.LoopAuthStrategy;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 用户会话模型
 * @create: 2022/07/20 19:38
 */
public class UserSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * token列表
     */
    private List<TokenModel> tokens = new ArrayList<>();

    public UserSession setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserSession setTokens(List<TokenModel> tokens) {
        this.tokens = tokens;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public List<TokenModel> getTokens() {
        return tokens;
    }

    public UserSession setToken(TokenModel tokenModel) {
        // 登录规则检测
        this.tokens = LoopAuthStrategy.loginRulesMatching.exe(this.tokens,tokenModel);
        return this;
    }

    public UserSession removeTokens(String... facilitys) {
        // 删除所选的会话  并 刷新会话
        this.setTokens(
                this.tokens.stream().filter(
                        tokenModel -> !Arrays.asList(facilitys)
                                .contains(tokenModel.getFacility())
                ).collect(Collectors.toList())
        );
        return this;
    }

    public UserSession removeToken(String token) {
        // 删除当前token
        // TODO: 2022/8/10
        return this;
    }

    public void setUserSession(){
        // 如果已经不存在会话则删除用户所有会话存储
        if (this.tokens.size() <= 0){
            // 删除会话
            LoopAuthStrategy.getLoopAuthDao().removeUserSession(userId);
        }else {
            LoopAuthStrategy.getLoopAuthDao().setUserSession(this);
        }
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId='" + userId + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
