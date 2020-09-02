package com.aihangxunxi.aitalk.storage.domain.msg.constant;

/**
 * 查询方向
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020-03-2020/3/18
 */
public enum QueryDirection {

	QUERY_NEW, // 查询比锚点时间更晚的消息
	QUERY_OLD// 查询比锚点时间更早的消息

}
