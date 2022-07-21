package com.sobercoding.loopauth.action;

/**
 * @author XHao
 * @date 2022年07月22日 0:00
 */
public interface LoopAuth {
    /**
     *
     * @param id 用户id
     * @return token值
     */
    String createToken(Long id);
}
