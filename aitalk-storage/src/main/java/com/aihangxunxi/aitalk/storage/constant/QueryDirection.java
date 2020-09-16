package com.aihangxunxi.aitalk.storage.constant;

/**
 * 查询方向
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public enum QueryDirection {

	QUERY_NEW, // 查询比锚点时间更晚的消息
	QUERY_OLD// 查询比锚点时间更早的消息

}
