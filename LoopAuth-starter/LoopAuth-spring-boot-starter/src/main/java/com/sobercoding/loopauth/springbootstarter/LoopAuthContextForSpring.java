package com.sobercoding.loopauth.springbootstarter;

import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.context.LoopAuthRequest;
import com.sobercoding.loopauth.context.LoopAuthResponse;
import com.sobercoding.loopauth.context.LoopAuthStorage;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthParamException;
import com.sobercoding.loopauth.servlet.context.LoopAuthRequestForServlet;
import com.sobercoding.loopauth.servlet.context.LoopAuthResponseForServlet;
import com.sobercoding.loopauth.servlet.context.LoopAuthStorageForServlet;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: Spring上下文
 * @create: 2022/08/10 18:34
 */
public class LoopAuthContextForSpring implements LoopAuthContext {
    @Override
    public LoopAuthRequest getRequest() {
        return new LoopAuthRequestForServlet(getServletRequestAttributes().getRequest());
    }

    @Override
    public LoopAuthResponse getResponse() {
        return new LoopAuthResponseForServlet(getServletRequestAttributes().getResponse());
    }

    @Override
    public LoopAuthStorage getStorage() {
        return new LoopAuthStorageForServlet(getServletRequestAttributes().getRequest());
    }

    private ServletRequestAttributes getServletRequestAttributes(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(servletRequestAttributes)
                .orElseThrow(() -> new LoopAuthParamException(LoopAuthExceptionEnum.PARAM_IS_NULL));
    }
}
