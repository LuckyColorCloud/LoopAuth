package com.sobercoding.loopauth.exception;

import com.sobercoding.loopauth.util.LoopAuthResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Yun
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常拦截  注解报错后 可在这里处理返回信息
     * @param e
     * @return
     */
    @ExceptionHandler
    public LoopAuthResult handlerException(Exception e) {
        e.printStackTrace();
        return LoopAuthResult.error(e.getMessage());
    }

}
