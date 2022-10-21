package com.sobercoding.loopauth.context;

import com.sobercoding.loopauth.context.SourceContext;

/**
 * [存储器] 包装类
 * <p> 在 Request作用域里: 存值、取值
 * @author Yun
 */
public interface LoopAuthStorage extends SourceContext {

	/**
	 * 在 [Request作用域] 里写入一个值 
	 * @param key 键 
	 * @param value 值
	 */
	void set(String key, Object value);
	
	/**
	 * 在 [Request作用域] 里获取一个值 
	 * @param key 键 
	 * @return 值 
	 */
	Object get(String key);

	/**
	 * 在 [Request作用域] 里删除一个值 
	 * @param key 键 
	 */
	void delete(String key);

}
