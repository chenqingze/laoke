package com.aihangxunxi.aitalk.im.session;

import java.time.Instant;
import java.util.Set;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @deprecated session
 */
public interface Session {

	/**
	 * 获取session id.{@link Session}
	 * @return session id. {@link Session}
	 */
	String getId();

	/**
	 * 获取session创建时间.
	 * @return session 被创建时间.
	 */
	Instant getCreationTime();

	/**
	 * 最后通讯时间
	 * @param lastCommunicatedTime 最后通讯时间
	 */
	void setLastCommunicatedTime(Instant lastCommunicatedTime);

	/**
	 * 获取最后通讯时间
	 * @return 最后通讯时间
	 */
	Instant getLastCommunicatedTime();

	/**
	 * 根据属性名attributeName获取session属性值
	 * @param attributeName 属性名
	 * @param <T> 返回属性值的类型
	 * @return 属性值
	 */
	<T> T getAttribute(String attributeName);

	/**
	 * 根据属性名获取属性值,如果属性值不存在则抛出异常{@link IllegalArgumentException}
	 * @param name 属性名
	 * @param <T> 属性值类型
	 * @return 属性值
	 */
	default <T> T getRequiredAttribute(String name) {
		T result = getAttribute(name);
		if (result == null) {
			throw new IllegalArgumentException("Required attribute '" + name + "' is missing.");
		}
		return result;
	}

	/**
	 * 根据属性名获取属性值,如果属性(为null)不存在,则返回默认的属性值
	 * @param name 属性名
	 * @param defaultValue 默认属性只
	 * @param <T> 返回属性值类型
	 * @return 属性值
	 */
	default <T> T getAttributeOrDefault(String name, T defaultValue) {
		T result = getAttribute(name);
		return (result != null) ? result : defaultValue;
	}

	/**
	 * 获取属性名集合
	 * @return 属性名集合
	 * @see #getAttribute(String)
	 */
	Set<String> getAttributeNames();

	/**
	 * 根据属性名设置属性值,如果属性值(为null)不存在,则删除此属性{@link Session#removeAttribute(String)}
	 * @param attributeName 属性名
	 * @param attributeValue 属性值
	 */
	void setAttribute(String attributeName, Object attributeValue);

	/**
	 * 根据属性名删除属性
	 * @param attributeName 属性名
	 */
	void removeAttribute(String attributeName);

}
