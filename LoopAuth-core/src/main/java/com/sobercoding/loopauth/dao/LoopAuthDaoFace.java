package com.sobercoding.loopauth.dao;

import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.model.UserSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/20 23:35
 */
public class LoopAuthDaoFace implements LoopAuthDao{


    /**
     * 登录状态缓存
     * 用户id 和 Token模型 键值对应
     * 等于缓存UserSession类数据 但是都序列化成String了
     */
    private Map<String, String> userSessions = new ConcurrentHashMap<>();


//    public UserSession getUserSession(System userId) {
//        String tokens = userSessions.get(userId);
//        Set<TokenModel> tokenModels =
//        UserSession userSession = UserSession.builder()
//                .build();
//        return userSession;
//    }

    public void setUserSession(UserSession userSession) {
        this.userSessions.put(
                userSession.getUserId(),
                userSession.getTokens().toString()
        );
    }
}
