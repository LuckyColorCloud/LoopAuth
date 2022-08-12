package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.LoopAuthStrategy;

import java.io.Serializable;
import java.util.*;
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
     * @Description: 根据设备删除token
     * @param facilitys
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/11 0:46
     */
    public UserSession removeTokenByFacility(String... facilitys) {
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
     * @Description: 删除会话token
     * @param tokens
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/11 0:44
     */
    public UserSession removeToken(String... tokens) {
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
     * @Description: 对内存的直接操作
     * 获取内存中的UserSession
     * @param
     * @Return: void
     * @Exception:
     * @Date: 2022/8/11 0:42
     */
    public UserSession getUserSession(){
        this.setTokens((List<TokenModel>) LoopAuthStrategy.getLoopAuthDao().get(this.getUserId()));
        return this;
    }

    /**
     * @Method: setUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 对内存的直接操作
     * 刷新会话存储，除remove以外，所有对于会话的更新操作均需要调用setUserSession进行存储刷新
     * @param
     * @Return: void
     * @Exception:
     * @Date: 2022/8/11 0:42
     */
    public void setUserSession(){
        // 如果已经不存在会话则删除用户所有会话存储
        if (this.getTokens().size() <= 0){
            // 删除会话
            remove();
        }else {
            LoopAuthStrategy.getLoopAuthDao().set(this.getUserId(), this.getTokens());
        }
    }

    /**
     * @Method: remove
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 对内存的直接操作，删除当前用户所有会话
     * @Return:
     * @Exception:
     * @Date: 2022/8/11 15:41
     */
    public void remove(){
        LoopAuthStrategy.getLoopAuthDao().remove(this.getUserId());
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId='" + userId + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
