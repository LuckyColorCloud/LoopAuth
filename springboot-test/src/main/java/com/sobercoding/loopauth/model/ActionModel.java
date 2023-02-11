package com.sobercoding.loopauth.model;

/**
 * @author Sober
 */
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

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public LoopAuthHttpMode getLoopAuthHttpMode() {
        return loopAuthHttpMode;
    }

    public void setLoopAuthHttpMode(LoopAuthHttpMode loopAuthHttpMode) {
        this.loopAuthHttpMode = loopAuthHttpMode;
    }
}
