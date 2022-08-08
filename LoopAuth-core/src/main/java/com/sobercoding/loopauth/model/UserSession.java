package com.sobercoding.loopauth.model;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;

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
    private Set<TokenModel> tokens = new HashSet<>();

    public UserSession setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserSession setTokens(Set<TokenModel> tokens) {
        this.tokens = tokens;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Set<TokenModel> getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId='" + userId + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
