package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.util.LoopAuthUtil;

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
    private List<TokenModel> tokens;

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
     * @Method: renewalToken
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 刷新token有效期
     * @param token
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/18 3:51
     */
    public UserSession renewalToken(String token) {
        this.getTokens().stream().filter(tokenModel -> token.equals(tokenModel.getValue())).findAny()
                .ifPresent(tokenModel -> tokenModel.setCreateTime(System.currentTimeMillis()));
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
        if (this.getTokens().stream().allMatch(TokenModel::isRemoveFlag)){
            // 删除会话
            remove();
        }else {
            Set<String> tokens = new HashSet<>();
            // 写入token对应模型
            this.getTokens().forEach(tokenModel -> {
                LoopAuthStrategy.getLoopAuthDao()
                        .set(
                                LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() + ":" + tokenModel.getValue(),
                                tokenModel
                        );
                // 组装tokens
                tokens.add(tokenModel.getValue());
            });
            // 写入loginId对应tokens
            LoopAuthStrategy.getLoopAuthDao()
                    .set(
                            LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix() + ":" + this.getUserId(),
                            tokens
                    );
        }
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
        Set<String> tokenSet = (Set<String>) LoopAuthStrategy.getLoopAuthDao()
                .get(LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix() +
                        ":" +
                        this.getUserId());
        List<TokenModel> tokenModels = new ArrayList<>();
        if (LoopAuthUtil.isNotEmpty(tokenSet)){
            tokenSet.forEach(token -> tokenModels
                    .add((TokenModel) LoopAuthStrategy.getLoopAuthDao()
                            .get(LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() +
                                    ":" +
                                    token))
            );
        }
        this.setTokens(tokenModels);
        return this;
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
        LoopAuthStrategy.getLoopAuthDao()
                .remove(
                        LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix() +
                        ":" +
                        this.getUserId()
                );
        this.getTokens()
                .forEach(
                        tokenModel -> LoopAuthStrategy.getLoopAuthDao()
                                .remove(LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() +
                                        ":" +
                                        tokenModel.getValue()
                )
        );
        this.setUserId(null);
        this.setTokens(null);
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
        this.tokens.stream()
                .filter(tokenModel -> Arrays.asList(facilitys).contains(tokenModel.getFacility()))
                .forEach(tokenModel -> tokenModel.setRemoveFlag(true));
        return this;
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
        this.tokens.stream()
                .filter(tokenModel -> Arrays.asList(tokens).contains(tokenModel.getValue()))
                .forEach(tokenModel -> tokenModel.setRemoveFlag(true));
        return this;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId='" + userId + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
