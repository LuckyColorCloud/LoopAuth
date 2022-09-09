package com.sobercoding.loopauth.springbootstarter;

import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthParamException;
import com.sobercoding.loopauth.servlet.context.LoopAuthRequestForServlet;
import com.sobercoding.loopauth.servlet.context.LoopAuthResponseForServlet;
import com.sobercoding.loopauth.servlet.context.LoopAuthStorageForServlet;
import com.sobercoding.loopauth.session.context.LoopAuthContext;
import com.sobercoding.loopauth.session.context.LoopAuthRequest;
import com.sobercoding.loopauth.session.context.LoopAuthResponse;
import com.sobercoding.loopauth.session.context.LoopAuthStorage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * Spring上下文
 * @author: Sober
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
                .orElseThrow(() -> new LoopAuthParamException(LoopAuthExceptionEnum.PARAM_IS_NULL, "ServletRequestAttributes Failed to get"));
    }
}
