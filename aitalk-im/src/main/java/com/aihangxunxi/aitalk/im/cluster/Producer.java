package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;

import java.io.IOException;

/**
 * AMQP P2P 生产者
 *
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/27 10:35 AM
 */
public interface Producer {

	/**
	 * 发送数据
	 * @param imNodeHostname imNodeHostname作为routerKey
	 * @param message 转发消息
	 */
	void send(String imNodeHostname, Message message) throws IOException;

}
