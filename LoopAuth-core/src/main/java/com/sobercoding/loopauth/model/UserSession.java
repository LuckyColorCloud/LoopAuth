package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.util.JsonUtil;

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


    /**
     * @Method: setToken
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 建立会话
     * @param tokenModel
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/10 23:51
     */
    public UserSession setToken(TokenModel tokenModel) {
        // 登录规则检测
        return this.setTokens(LoopAuthStrategy.loginRulesMatching.exe(this.getTokens(),tokenModel));
    }

    /**
     * @Method: removeByFacility
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 根据设备删除会话
     * @param facilitys
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/11 0:46
     */
    public UserSession removeByFacility(String... facilitys) {
        return this.setTokens(
                this.getTokens().stream().filter(
                        tokenModel -> !Arrays.asList(facilitys)
                                .contains(tokenModel.getFacility())
                ).collect(Collectors.toList())
        );
    }



    /**
     * @Method: removeToken
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 根据Token删除会话
     * @param tokens
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/11 0:44
     */
    public UserSession removeByToken(String... tokens) {
        return this.setTokens(
                this.getTokens().stream().filter(
                        tokenModel -> !Arrays.asList(tokens)
                                .contains(tokenModel.getValue())
                ).collect(Collectors.toList())
        );
    }

    /**
     * @Method: setUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 存储会话到缓存
     * @param
     * @Return: void
     * @Exception:
     * @Date: 2022/8/11 0:42
     */
    public void setUserSession(){
        // 如果已经不存在会话则删除用户所有会话存储
        if (getTokens().size() <= 0){
            // 删除会话
            LoopAuthStrategy.getLoopAuthDao().remove(this.getUserId());
        }else {
            LoopAuthStrategy.getLoopAuthDao().set(this.getUserId(), JsonUtil.objToJson(this.getTokens()));
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
