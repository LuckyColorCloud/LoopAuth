package com.sobercoding.loopauth.springbootstarter.interceptor;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.model.AbacPoAndSu;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.function.MaFunction;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.session.context.LoopAuthRequest;
import com.sobercoding.loopauth.springbootstarter.CheckPermissionAnnotation;
import com.sobercoding.loopauth.util.LoopAuthUtil;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

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
            // 当前请求方式
            LoopAuthHttpMode loopAuthHttpMode = LoopAuthHttpMode.toEnum(req.getMethod());
            Optional<Set<Policy>> policy = Optional.ofNullable(AbacStrategy.getAbacInterface().getPolicySet(route, loopAuthHttpMode));
            policy.ifPresent(policies -> policies.forEach(item -> {
                Set<String> keySet = item.getPropertyMap().keySet();
                keySet.forEach(key -> {
                    Optional.ofNullable(AbacStrategy.abacPoAndSuMap
                            .get(key))
                            .ifPresent(abacPoAndSu -> {
                                abacPoAndSu.getMaFunction()
                                        .mate(abacPoAndSu.getSupplierMap().get(), item.getPropertyMap().get(key));
                            });
                });
            }));
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
