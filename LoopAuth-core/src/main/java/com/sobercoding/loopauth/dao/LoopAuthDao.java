package com.sobercoding.loopauth.dao;

import com.sobercoding.loopauth.model.UserSession;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/20 23:35
 */
public interface LoopAuthDao {

    /**
     * @Method: getUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取用户会话
     * @param userId 用户id
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    UserSession getUserSession(String userId);

    /**
     * @Method: setUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 写入用户会话
     * @param userSession 会话模型
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    void setUserSession(UserSession userSession);

    /**
     * @Method: setUserSession
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 清空登录状态
     * @param userId 用户id
     * @Return:
     * @Exception:
     * @Date: 2022/8/8 17:16
     */
    void removeUserSession(String userId);

}
