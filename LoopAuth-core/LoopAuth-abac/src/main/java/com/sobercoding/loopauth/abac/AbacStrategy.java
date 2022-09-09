package com.sobercoding.loopauth.abac;

import com.sobercoding.loopauth.function.MaFunction;

import java.util.*;

/**
 * LoopAuth  Bean管理器
 * @author: Sober
 */

public class AbacStrategy {

    /**
     * ABAC鉴权匹配方式
     */
    public volatile static Map<String, MaFunction> maFunctionMap = new HashMap<>();


}
