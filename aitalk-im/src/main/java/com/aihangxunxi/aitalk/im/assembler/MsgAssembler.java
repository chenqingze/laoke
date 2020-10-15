package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.*;
import com.aihangxunxi.aitalk.storage.constant.ConversationType;
import com.aihangxunxi.aitalk.storage.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.constant.MsgType;
import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * todo: 完善数据转换
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Component
public class MsgAssembler {

	@Resource
	private UserRepository userRepository;

	public MsgAck buildMsgAck() {
		return MsgAck.newBuilder().build();
	}

	public MsgReadNotify buildMsgReadNotify(MsgHist msgHist) {
		MsgReadNotify msgReadNotify = MsgReadNotify.newBuilder().setMsgId(msgHist.getMsgId().toHexString())
				.setSenderId(String.valueOf(msgHist.getSenderId()))
				.setConversationId(msgHist.getReceiverId().toHexString())
				.setConversationType(msgHist.getConversationType().ordinal())
				.setMsgStatus(msgHist.getMsgStatus().ordinal()).setMsgType(msgHist.getMsgType().ordinal())
				.setContent(msgHist.getContent()).setCreatedAt(msgHist.getCreatedAt())
				.setUpdatedAt(msgHist.getUpdatedAt()).build();
		return msgReadNotify;
	}

	public MsgHist convertMsgRequestToMsgHist(Message message) {
		MsgRequest msgRequest = message.getMsgRequest();
		MsgHist msgHist = new MsgHist();
		msgHist.setReceiverId(new ObjectId(msgRequest.getConversationId()));
		msgHist.setContent(msgRequest.getContent());
		msgHist.setMsgType(MsgType.TEXT);
		return msgHist;
	}

	public Message convertMgsHistToMessage(MsgHist msgHist, long seq) {
		Message msgAck = Message.newBuilder().setOpCode(OpCode.MSG_ACK).setSeq(seq)
				.setMsgAck(MsgAck.newBuilder().setMsgId(msgHist.getMsgId().toHexString())
						.setConversationType(com.aihangxunxi.aitalk.im.protocol.buffers.ConversationType
								.forNumber(msgHist.getConversationType().ordinal()))
						.setCreatedAt(msgHist.getCreatedAt()).build())
				.build();
		return msgAck;
	}

	public MucHist convertToMucHist(Message message) {
		MsgRequest msgRequest = message.getMsgRequest();
		MucHist mucHist = new MucHist();
		mucHist.setMsgId(new ObjectId());
		mucHist.setMsgType(MsgType.codeOf(msgRequest.getMsgType().getNumber()));
		mucHist.setConversationType(ConversationType.codeOf(msgRequest.getConversationType().getNumber()));
		mucHist.setSenderId(Long.parseLong(msgRequest.getSenderId()));
		mucHist.setMsgStatus(MsgStatus.SENDING);
		mucHist.setContent(msgRequest.getContent());
		mucHist.setCreatedAt(new Date().getTime());
		mucHist.setReceiverId(new ObjectId(msgRequest.getConversationId()));
		return mucHist;
	}

	public Message convertMucHistToMessage(MucHist mucHist, String seq) {
		Message message = Message.newBuilder().setOpCode(OpCode.MSG_ACK).setSeq(Long.parseLong(seq))
				.setMsgAck(MsgAck.newBuilder()
						.setConversationType(com.aihangxunxi.aitalk.im.protocol.buffers.ConversationType
								.forNumber(mucHist.getConversationType().ordinal()))
						.setMsgId(mucHist.getMsgId().toHexString()).build())
				.build();
		return message;
	}

	public MsgHist convertMsgReadAckToMsgHist(Message msg) {
		MsgReadAck msgReadAck = msg.getMsgReadAck();
		MsgHist msgHist = new MsgHist();
		msgHist.setMsgId(new ObjectId(msgReadAck.getMsgId()));
		return msgHist;
	}

}
