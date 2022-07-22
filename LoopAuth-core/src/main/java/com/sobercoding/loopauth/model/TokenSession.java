package com.sobercoding.loopauth.model;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Sober
 * @date 2022-07-22 17:41
 */
public class TokenSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * token列表
     */
    private Set<String> tokens = new ConcurrentSkipListSet<>();

}
