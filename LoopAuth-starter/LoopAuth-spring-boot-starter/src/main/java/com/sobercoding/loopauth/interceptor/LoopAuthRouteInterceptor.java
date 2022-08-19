package com.sobercoding.loopauth.interceptor;

import com.sobercoding.loopauth.face.LoopAuthFaceImpl;
import com.sobercoding.loopauth.router.LoopAuthRouteFunction;
import com.sobercoding.loopauth.servlet.model.LoopAuthRequestForServlet;
import com.sobercoding.loopauth.servlet.model.LoopAuthResponseForServlet;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Loop-Auth基于路由的拦截式鉴权
 * @author Yun
 */
public class LoopAuthRouteInterceptor implements HandlerInterceptor {

	/**
	 * 每次进入拦截器的[执行函数]，默认为登录校验
	 */
	public LoopAuthRouteFunction function = (req, res, handler) -> LoopAuthFaceImpl.isLogin();

	/**
	 * 创建一个路由拦截器
	 */
	public LoopAuthRouteInterceptor() {
	}

	/**
	 * 创建, 并指定[执行函数]
	 * @param function [执行函数]
	 */
	public LoopAuthRouteInterceptor(LoopAuthRouteFunction function) {
		this.function = function;
	}

	/**
	 * 静态方法快速构建一个 
	 * @param function 自定义模式下的执行函数
	 * @return LoopAuth路由拦截器
	 */
	public static LoopAuthRouteInterceptor newInstance(LoopAuthRouteFunction function) {
		return new LoopAuthRouteInterceptor(function);
	}
	
	
	// ----------------- 验证方法 ----------------- 

	/**
	 * 每次请求之前触发的方法 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			function.run(new LoopAuthRequestForServlet(request), new LoopAuthResponseForServlet(response), handler);
		} catch (Exception e) {
			// 停止匹配，向前端输出结果 
			if(response.getContentType() == null) {
				response.setContentType("text/plain; charset=utf-8"); 
			}
			response.getWriter().print(e.getMessage());
			return false;
		}
		
		// 通过验证 
		return true;
	}

}
