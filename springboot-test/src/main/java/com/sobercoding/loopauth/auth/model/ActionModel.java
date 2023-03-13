package com.sobercoding.loopauth.auth.model;

import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import org.springframework.stereotype.Component;

/**
 * @author Sober
 */
@Component
public class ActionModel {

    /**
     * 请求方式
     */
    private LoopAuthHttpMode loopAuthHttpMode;

    /**
     * 操作表述
     */
    private String doc;

    public String getDoc() {
        return doc;
    }

    public LoopAuthHttpMode getLoopAuthHttpMode() {
        return loopAuthHttpMode;
    }

}
