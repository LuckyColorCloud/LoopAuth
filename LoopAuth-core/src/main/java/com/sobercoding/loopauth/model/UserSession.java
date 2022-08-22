package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
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
        tokens = LoopAuthStrategy.loginRulesMatching.exe(
                tokens.stream().filter(item -> !item.isRemoveFlag()).collect(Collectors.toSet()),
                tokenModel);
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
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 对内存的直接操作
     * 获取内存中用户的所有UserSession
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
            // 过滤过期空值之后组装 Set<TokenModel>
            tokenSet.stream().filter(LoopAuthUtil::isNotEmpty).forEach(token ->
                    tokenModels.add(new TokenModel().setValue(token).gainTokenModel())
            );
        }
        this.tokens = tokenModels.stream().filter(LoopAuthUtil::isNotEmpty).collect(Collectors.toSet());
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
        this.getTokens().forEach(TokenModel::remove);
        this.loginId = null;
        this.tokens = null;
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
