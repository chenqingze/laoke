package com.aihangxunxi.aitalk.im.cluster;

import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * AMQP P2P 消费者
 *
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/27 10:35 AM
 */
public interface Consumer {

	void receive(DeliverCallback deliverCallback) throws IOException;

}
