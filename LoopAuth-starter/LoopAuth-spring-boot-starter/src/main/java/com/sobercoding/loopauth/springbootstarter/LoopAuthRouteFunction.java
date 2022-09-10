package com.sobercoding.loopauth.springbootstarter;


import com.sobercoding.loopauth.session.context.LoopAuthRequest;
import com.sobercoding.loopauth.session.context.LoopAuthResponse;

/**
 * 路由拦截器验证方法Lambda 
 * 
 * @author Yun
 *
 */
@FunctionalInterface
public interface LoopAuthRouteFunction {

	/**
	 * 执行验证的方法
	 * 
	 * @param request  Request包装对象
	 * @param response Response包装对象
	 * @param handler  处理对象
	 */
	void run(LoopAuthRequest request, LoopAuthResponse response, Object handler);

}
