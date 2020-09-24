package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.MsgAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.MsgReadNotify;
import com.aihangxunxi.aitalk.im.protocol.buffers.MsgRequest;
import com.aihangxunxi.aitalk.storage.constant.ConversationType;
import com.aihangxunxi.aitalk.storage.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.constant.MsgType;
import com.aihangxunxi.aitalk.storage.model.Msg;
import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Date;

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

	public MucHist convertToMucHist(Message message) {
		MsgRequest msgRequest = message.getMsgRequest();
		MucHist mucHist = new MucHist();

		mucHist.setMsgType(MsgType.codeOf(msgRequest.getMsgType()));
		mucHist.setConversationType(ConversationType.codeOf(msgRequest.getConversationType()));
		mucHist.setSenderId(new ObjectId(msgRequest.getSenderId()));
		mucHist.setMsgStatus(MsgStatus.SENDING);
		mucHist.setContent(msgRequest.getContent());
		mucHist.setCreatedAt(new Date().getTime());
		mucHist.setReceiverId(new ObjectId(msgRequest.getConversationId()));
		return mucHist;
	}

	public Message convertMucHistToMessage(MucHist mucHist, String seq) {
		Message message = Message.newBuilder()
				.setMsgAck(MsgAck.newBuilder().setMsgId(mucHist.getMsgId().toHexString()).setSeq(seq).build()).build();
		return message;
	}

}
