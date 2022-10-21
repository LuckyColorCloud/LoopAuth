package com.sobercoding.loopauth.springbootwebfluxstarter.context;

import com.sobercoding.loopauth.session.context.LoopAuthStorage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * Servlet 的存储
 * @author Yun
 *
 */
public class LoopAuthStorageForWebFlux implements LoopAuthStorage {

	/**
	 * 底层Request对象
	 */
	protected ServerWebExchange exchange;
	
	/**
	 * 实例化
	 * @param exchange request对象
	 */
	public LoopAuthStorageForWebFlux(ServerWebExchange exchange) {
		this.exchange = exchange;
	}
	
	/**
	 * 获取底层源对象 
	 */
	@Override
	public Object getSource() {
		return exchange;
	}

	/**
	 * 在 [Request作用域] 里写入一个值 
	 */
	@Override
	public void set(String key, Object value) {
		exchange.getAttributes().put(key, value);
	}

	/**
	 * 在 [Request作用域] 里获取一个值 
	 */
	@Override
	public Object get(String key) {
		return exchange.getAttributes().get(key);
	}

	/**
	 * 在 [Request作用域] 里删除一个值 
	 */
	@Override
	public void delete(String key) {
		exchange.getAttributes().remove(key);
	}

}
