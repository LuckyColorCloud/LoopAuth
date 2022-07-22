package com.sobercoding.loopauth.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/20 23:35
 */
public class LoopAuthDaoFace implements LoopAuthDao{


    // token 和 用户id键值对应
    private Map<String,String> tokenUser = new ConcurrentHashMap<>();
}
