package com.sobercoding.loopauth.model;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

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
    private Set<TokenModel> tokens;

    public String getUserId() {
        return userId;
    }

    public Set<TokenModel> getTokens() {
        return tokens;
    }

    public UserSession (Builder builder){
        this.userId = builder.userId;
        this.tokens = builder.tokens;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String userId;

        private Set<TokenModel> tokens = new HashSet<>();

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder tokenModel(TokenModel tokenModel) {
            this.tokens.add(tokenModel);
            return this;
        }

        public UserSession build(){
            return new UserSession(this);
        }
    }

}
