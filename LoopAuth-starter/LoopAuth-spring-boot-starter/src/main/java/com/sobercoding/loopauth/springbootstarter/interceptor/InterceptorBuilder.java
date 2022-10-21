package com.sobercoding.loopauth.springbootstarter.interceptor;

import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.springbootstarter.CheckPermissionAnnotation;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * 拦截器组装类
 * @author Sober
 */
public class InterceptorBuilder {

    LoopAuthInterceptor loopAuthInterceptor = new LoopAuthInterceptor();

    public Builder Abac() {
        loopAuthInterceptor.function = (req, res, handler) -> {
            // 路由
            String route = req.getRequestPath();
            // ABAC鉴权
            LoopAuthAbac.check(route, req.getMethod());
        };
        return new Builder();
    }

    public Builder Annotation() {
        loopAuthInterceptor.function = (req, res, handler) -> {
            // 获取处理method
            if (handler instanceof HandlerMethod) {
                Method method = ((HandlerMethod) handler).getMethod();
                // 进行验证
                CheckPermissionAnnotation.checkMethodAnnotation.accept(method);
            }
        };
        return new Builder();
    }
    public Builder Route() {
        loopAuthInterceptor.function = (req, res, handler) -> LoopAuthSession.isLogin();
        return new Builder();
    }

    public class Builder {
        public LoopAuthInterceptor builder() {
            return loopAuthInterceptor;
        }
    }

}
