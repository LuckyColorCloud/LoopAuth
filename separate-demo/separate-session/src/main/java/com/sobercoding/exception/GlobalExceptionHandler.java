package com.sobercoding.exception;

import com.sobercoding.loopauth.exception.LoopAuthException;
import com.sobercoding.model.Result;
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
     */
    @ExceptionHandler
    public Result handlerException(LoopAuthException e) {
        return Result.error(e.getMessage());
    }

}
