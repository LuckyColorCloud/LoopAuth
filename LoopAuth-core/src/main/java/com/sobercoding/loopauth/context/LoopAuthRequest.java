package com.sobercoding.loopauth.context;

/**
 * Request 包装类
 * @author Yun
 */
public interface LoopAuthRequest extends SourceContext{
	

	/**
	 * 在 [请求体] 里获取一个值
	 * @param name 键
	 * @return 值
	 */
	public String getParam(String name);


	/**
	 * 在 [Cookie作用域] 里获取一个值 
	 * @param name 键 
	 * @return 值 
	 */
	public String getCookieValue(String name);

	/**
	 * 返回当前请求path (不包括上下文名称) 
	 * @return see note
	 */
	public String getRequestPath();

	/**
	 * 返回当前请求path是否为指定值 
	 * @param path path 
	 * @return see note
	 */
	public default boolean isPath(String path) {
		return getRequestPath().equals(path);
	}

	/**
	 * 返回当前请求的类型 
	 * @return see note
	 */
	public String getMethod();


	/**
	 * 在 [请求头] 里获取一个值
	 * @param name 键
	 * @return 值
	 */
	public String getHeader(String name);


	
}
