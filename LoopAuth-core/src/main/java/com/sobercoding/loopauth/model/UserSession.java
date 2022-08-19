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
        this.tokens = LoopAuthStrategy.loginRulesMatching.exe(tokens,tokenModel);
        // 登录规则检测
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
        if (tokens.stream().allMatch(TokenModel::isRemoveFlag)){
            // 删除会话
            remove();
        }else {
            // 过滤需要删除的会话
            tokens = tokens.stream().filter(item -> !item.isRemoveFlag()).collect(Collectors.toSet());
            Set<String> tokenValues = new HashSet<>();
            // 写入token对应模型
            tokens.forEach(tokenModel -> {
                LoopAuthStrategy.getLoopAuthDao()
                        .set(
                                LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() + ":" + tokenModel.getValue(),
                                tokenModel
                        );
                // 组装tokens
                tokenValues.add(tokenModel.getValue());
            });
            // 写入loginId对应tokens
            LoopAuthStrategy.getLoopAuthDao()
                    .set(
                            LoopAuthStrategy.getLoopAuthConfig().getLoginIdPersistencePrefix() + ":" + loginId,
                            tokenValues
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
                        loginId);
        Set<TokenModel> tokenModels = new HashSet<>();
        if (LoopAuthUtil.isNotEmpty(tokenSet)){
            tokenSet.forEach(token -> tokenModels
                    .add((TokenModel) LoopAuthStrategy.getLoopAuthDao()
                            .get(LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() +
                                    ":" +
                                    token))
            );
        }
        this.tokens = tokenModels;
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
                        loginId
                );
        this.getTokens()
                .forEach(
                        tokenModel -> LoopAuthStrategy.getLoopAuthDao()
                                .remove(LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() +
                                        ":" +
                                        tokenModel.getValue()
                )
        );
        this.loginId = null;
        this.tokens = null;
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
        tokens.stream()
                .filter(tokenModel -> Arrays.asList(facilitys).contains(tokenModel.getFacility()))
                .forEach(tokenModel -> tokenModel.setRemoveFlag(true));
        return this;
    }



    /**
     * @Method: removeToken
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 删除会话token
     * @param tokenModelValues token值
     * @Return: com.sobercoding.loopauth.model.UserSession
     * @Exception:
     * @Date: 2022/8/11 0:44
     */
    public UserSession removeToken(String... tokenModelValues) {
        tokens.stream()
                .filter(tokenModel -> Arrays.asList(tokenModelValues).contains(tokenModel.getValue()))
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
