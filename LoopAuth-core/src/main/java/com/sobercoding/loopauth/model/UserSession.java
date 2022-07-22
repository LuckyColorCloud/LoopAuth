package com.sobercoding.loopauth.model;

import java.io.Serializable;
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
    private Map<String,TokenModel> tokens = new ConcurrentHashMap<>();

}
