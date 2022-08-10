package com.sobercoding.loopauth.annotation;

/**
 * 注解鉴权的验证模式
 *  且 或 非
 * @author Yun
 */
public enum LoopAuthMode {

	/**
	 * 必须具有所有的元素 
	 */
	AND,

	/**
	 * 只需具有其中一个元素
	 */
	OR,
	/**
	 * 不是该权限的 即可访问
	 */
	NON
	
}
