package com.sobercoding.loopauth.session.context;

import com.sobercoding.loopauth.exception.LoopAuthConfigException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;

import javax.swing.*;
import java.util.Optional;

/**
 * ThreadLocal 上下文存储器
 * @author Sober
 */
public class LoopAuthContextThreadLocal implements LoopAuthContext{

    /**
     * ThreadLocal 的 存储器
     */
    public static ThreadLocal<ContextBox> contextBoxThreadLocal = new InheritableThreadLocal<ContextBox>();


    @Override
    public LoopAuthRequest getRequest() {
        return getContextBox().getLoopAuthRequest();
    }

    @Override
    public LoopAuthResponse getResponse() {
        return getContextBox().getLoopAuthResponse();
    }

    @Override
    public LoopAuthStorage getStorage() {
        return getContextBox().getLoopAuthStorage();
    }

    /**
     * 写入contextBoxThreadLocal
     * @author Sober
     * @param loopAuthRequest
     * @param loopAuthResponse
     * @param loopAuthStorage
     */
    public static void setContext(LoopAuthRequest loopAuthRequest,LoopAuthResponse loopAuthResponse,LoopAuthStorage loopAuthStorage) {
        ContextBox contextBox = new ContextBox(loopAuthRequest,loopAuthResponse,loopAuthStorage);
        contextBoxThreadLocal.set(contextBox);
    }

    /**
     * 清除 contextBoxThreadLocal
     * @author Sober
     */
    public static void clearContextBox(){
        contextBoxThreadLocal.remove();
    }

    /**
     * 获取ContextBox
     * @author Sober
     * @return com.sobercoding.loopauth.session.context.LoopAuthContextThreadLocal.ContextBox
     */
    public static ContextBox getContextBox() {
        Optional<ContextBox> contextBoxOptional = Optional.ofNullable(contextBoxThreadLocal.get());
        contextBoxOptional.orElseThrow(() -> new LoopAuthConfigException(LoopAuthExceptionEnum.CONFIGURATION_UNREALIZED,"Context"));
        return contextBoxOptional.get();
    }

    /**
     * 上下文盒子
     * @author Sober
     */
    public static class ContextBox {

        private LoopAuthRequest loopAuthRequest;

        private LoopAuthResponse loopAuthResponse;

        private LoopAuthStorage loopAuthStorage;

        public ContextBox(LoopAuthRequest loopAuthRequest, LoopAuthResponse loopAuthResponse, LoopAuthStorage loopAuthStorage) {
            this.loopAuthRequest = loopAuthRequest;
            this.loopAuthResponse = loopAuthResponse;
            this.loopAuthStorage = loopAuthStorage;
        }

        public LoopAuthRequest getLoopAuthRequest() {
            return loopAuthRequest;
        }

        public ContextBox setLoopAuthRequest(LoopAuthRequest loopAuthRequest) {
            this.loopAuthRequest = loopAuthRequest;
            return this;
        }

        public LoopAuthResponse getLoopAuthResponse() {
            return loopAuthResponse;
        }

        public ContextBox setLoopAuthResponse(LoopAuthResponse loopAuthResponse) {
            this.loopAuthResponse = loopAuthResponse;
            return this;
        }

        public LoopAuthStorage getLoopAuthStorage() {
            return loopAuthStorage;
        }

        public ContextBox setLoopAuthStorage(LoopAuthStorage loopAuthStorage) {
            this.loopAuthStorage = loopAuthStorage;
            return this;
        }

        @Override
        public String toString() {
            return "ContextBox{" +
                    "loopAuthRequest=" + loopAuthRequest +
                    ", loopAuthResponse=" + loopAuthResponse +
                    ", loopAuthStorage=" + loopAuthStorage +
                    '}';
        }
    }
}
