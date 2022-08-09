package com.sobercoding.loopauth.router;

import com.sobercoding.loopauth.exception.LoopAuthException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Http 请求各种请求类型的枚举表示
 * @author Yun
 */
@SuppressWarnings("ALL")
public enum LoopAuthHttpMethod {
	
	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE, CONNECT, 
	
	/**
	 * 代表全部请求方式 
	 */
	ALL;
	
	private static final Map<String, LoopAuthHttpMethod> map = new HashMap<>();

	static {
		for (LoopAuthHttpMethod reqMethod : values() ) {
			map.put(reqMethod.name(), reqMethod);
		}
	}

	/**
	 * String 转 enum 
	 * @param method 请求类型 
	 * @return ReqMethod 对象 
	 */
	public static LoopAuthHttpMethod toEnum(String method) {
		return Optional.ofNullable(
				Optional.ofNullable(method)
						.map(str -> map.get(method.toUpperCase()))
						.orElseThrow(() -> new LoopAuthException(LoopAuthExceptionEnum.HTTP_MODULE_ERROR))
		).get();
	}

	/**
	 * String[] 转 enum[]
	 * @param methods 请求类型数组 
	 * @return ReqMethod 对象 
	 */
	public static LoopAuthHttpMethod[] toEnumArray(String... methods) {
		LoopAuthHttpMethod [] arr = new LoopAuthHttpMethod[methods.length];
		for (int i = 0; i < methods.length; i++) {
			arr[i] = LoopAuthHttpMethod.toEnum(methods[i]);
		}
		return arr;
	}

}
