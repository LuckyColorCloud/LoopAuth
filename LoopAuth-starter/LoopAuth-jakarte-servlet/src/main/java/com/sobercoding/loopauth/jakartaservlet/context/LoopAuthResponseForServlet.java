package com.sobercoding.loopauth.jakartaservlet.context;

import com.sobercoding.loopauth.context.LoopAuthResponse;
import jakarta.servlet.http.HttpServletResponse;


/**
 * 对 Servlet 的响应
 *
 * @author Yun
 */
public class LoopAuthResponseForServlet implements LoopAuthResponse {

	/**
	 * 底层Request对象
	 */
	protected HttpServletResponse response;
	/**
	 * 实例化
	 * @param response response对象
	 */
	public LoopAuthResponseForServlet(HttpServletResponse response) {
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
		response.setStatus(sc);
		return this;
	}
	
	/**
	 * 在响应头里写入一个值 
	 */
	@Override
	public LoopAuthResponse setHeader(String name, String value) {
		response.setHeader(name, value);
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
		response.addHeader(name, value);
		return this;
	}

	
}
