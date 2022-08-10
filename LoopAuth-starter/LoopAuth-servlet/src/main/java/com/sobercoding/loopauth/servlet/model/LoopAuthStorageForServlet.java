package com.sobercoding.loopauth.servlet.model;

import com.sobercoding.loopauth.context.LoopAuthStorage;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet 的存储
 * @author Yun
 *
 */
public class LoopAuthStorageForServlet implements LoopAuthStorage {

	/**
	 * 底层Request对象
	 */
	protected HttpServletRequest request;
	
	/**
	 * 实例化
	 * @param request request对象 
	 */
	public LoopAuthStorageForServlet(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * 获取底层源对象 
	 */
	@Override
	public Object getSource() {
		return request;
	}

	/**
	 * 在 [Request作用域] 里写入一个值 
	 */
	@Override
	public void set(String key, Object value) {
		request.setAttribute(key, value);
	}

	/**
	 * 在 [Request作用域] 里获取一个值 
	 */
	@Override
	public Object get(String key) {
		return request.getAttribute(key);
	}

	/**
	 * 在 [Request作用域] 里删除一个值 
	 */
	@Override
	public void delete(String key) {
		request.removeAttribute(key);
	}

}
