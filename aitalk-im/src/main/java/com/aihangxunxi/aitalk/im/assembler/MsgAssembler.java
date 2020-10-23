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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
						.setSenderId(msgHist.getSenderId().toHexString())
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
		mucHist.setMsgStatus(MsgStatus.SUCCESS);
		mucHist.setContent(msgRequest.getContent());
		mucHist.setCreatedAt(new Date().getTime());
		mucHist.setReceiverId(new ObjectId(msgRequest.getConversationId()));
		return mucHist;
	}

	public Message convertToMucRequest(MucHist mucHist, Long seq) {
		return Message.newBuilder().setSeq(seq).setOpCode(OpCode.MSG_REQUEST).setMsgRequest(MsgRequest.newBuilder()
				.setContent(mucHist.getContent()).setConversationId(mucHist.getReceiverId().toHexString())
				.setConversationType(com.aihangxunxi.aitalk.im.protocol.buffers.ConversationType.MUC)
				.setMsgDirection("OUT").setMsgId(mucHist.getMsgId().toHexString())
				.setSenderId(mucHist.getSenderId().toString())
				.setMsgType(
						com.aihangxunxi.aitalk.im.protocol.buffers.MsgType.forNumber(mucHist.getMsgType().ordinal()))
				.setMsgStatus(com.aihangxunxi.aitalk.im.protocol.buffers.MsgStatus.SUCCESS).build()).build();

	}

	public Message convertMucHistToMessage(MucHist mucHist, Long seq) {
		Message message = Message.newBuilder().setOpCode(OpCode.MSG_ACK).setSeq(seq)
				.setMsgAck(MsgAck.newBuilder().setSeq(String.valueOf(seq))
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

	public Message buildInitMucHist(List<MucHist> list, Long seq) {
		List<MucBody> mucBodies = new ArrayList<>();

		for (MucHist mucHist : list) {
			MucBody mucBody = MucBody.newBuilder().setContent(mucHist.getContent())
					.setConversationId(mucHist.getReceiverId().toHexString())
					.setSenderId(mucHist.getSenderId().toString())
					.setConversationType(com.aihangxunxi.aitalk.im.protocol.buffers.ConversationType.MUC)
					.setMsgStatus(com.aihangxunxi.aitalk.im.protocol.buffers.MsgStatus.SUCCESS)
					.setMsgId(mucHist.getMsgId().toHexString())
					.setMsgType(com.aihangxunxi.aitalk.im.protocol.buffers.MsgType
							.forNumber(mucHist.getMsgType().ordinal()))
					.setTime(mucHist.getCreatedAt()).build();
			mucBodies.add(mucBody);
		}

		return Message.newBuilder().setOpCode(OpCode.PULL_MUC_HIST_ACK).setSeq(seq).setPullMucHistAck(
				PullMucHistAck.newBuilder().setMessage("群组聊天记录初始化成功").setSuccess("ok").addAllMuc(mucBodies).build())
				.build();
	}

}
