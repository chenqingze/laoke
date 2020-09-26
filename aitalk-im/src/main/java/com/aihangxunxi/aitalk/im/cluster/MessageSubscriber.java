package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/26 2:32 PM
 * @deprecated
 */
public interface MessageSubscriber {

	void onMessage(final Message message, final byte[] pattern);

}
