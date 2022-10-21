package com.sobercoding.loopauth.springbootwebfluxstarter.context;

import com.sobercoding.loopauth.context.LoopAuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;

/**
 * 对 Servlet 的响应
 *
 * @author Yun
 */
public class LoopAuthResponseForWebFlux implements LoopAuthResponse {

	/**
	 * 底层Request对象
	 */
	protected ServerHttpResponse response;
	/**
	 * 实例化
	 * @param response response对象
	 */
	public LoopAuthResponseForWebFlux(ServerHttpResponse response) {
		this.response = response;
	}

	/**
	 * 获取底层源对象 
	 */
	@Override
	public Object getSource() {
		return response;
	}

	/**
	 * 设置响应状态码 
	 */
	@Override
	public LoopAuthResponse setStatus(int sc) {
		response.setStatusCode(HttpStatus.valueOf(sc));
		return this;
	}
	
	/**
	 * 在响应头里写入一个值 
	 */
	@Override
	public LoopAuthResponse setHeader(String name, String value) {
		response.getHeaders().set(name, value);
		return this;
	}


	/**
	 * 在响应头里添加一个值 
	 * @param name 名字
	 * @param value 值 
	 * @return 对象自身 
	 */
	@Override
	public LoopAuthResponse addHeader(String name, String value) {
		response.getHeaders().add(name, value);
		return this;
	}

	
}
