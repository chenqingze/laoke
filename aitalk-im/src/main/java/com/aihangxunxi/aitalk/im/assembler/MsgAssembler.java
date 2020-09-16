package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.MsgAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.MsgReadNotify;
import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import org.springframework.stereotype.Component;

/**
 * todo: 完善数据转换
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Component
public class MsgAssembler {

	MsgAck buildMsgAck() {
		return MsgAck.newBuilder().build();
	}

	MsgReadNotify buildMsgReadNotify() {
		return MsgReadNotify.newBuilder().build();
	}

	MsgHist convertToMsgHist(Message message) {
		message.getMsgRequest();
		return null;
	}

	MucHist convertToMucHist(Message message) {
		message.getMsgRequest();
		return null;
	}

}
