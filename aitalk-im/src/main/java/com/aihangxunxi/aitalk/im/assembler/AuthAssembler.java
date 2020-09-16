package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.AuthAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import org.springframework.stereotype.Component;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Component
public class AuthAssembler {

	public Message authAckBuilder(Long seq, String sessionId, boolean result) {
		return Message.newBuilder().setSeq(seq).setOpCode(OpCode.AUTH_ACK)
				.setAuthAck(AuthAck.newBuilder().setResult(result).setSessionId(sessionId)).build();
	}

}
