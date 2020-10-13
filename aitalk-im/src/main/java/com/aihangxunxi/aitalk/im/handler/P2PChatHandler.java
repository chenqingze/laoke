package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.MsgAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.*;
import com.aihangxunxi.aitalk.storage.constant.ConversationType;
import com.aihangxunxi.aitalk.storage.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.model.*;
import com.aihangxunxi.aitalk.storage.repository.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class P2PChatHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(P2PChatHandler.class);

	@Resource
	private ChannelManager channelManager;

	@Resource
	private MsgHistRepository msgHistRepository;

	@Resource
	private MsgAssembler msgAssembler;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.MSG_REQUEST) {
			if (ConversationType.P2P.ordinal() == ((Message) msg).getMsgRequest().getConversationType()) {

				MsgHist msgHist = msgAssembler.convertMsgRequestToMsgHist((Message) msg);
				msgHist.setSenderId(new ObjectId("5f6d3f65e62333c82048ec8c"));
				msgHist.setMsgStatus(MsgStatus.SUCCESS);
				msgHist.setConversationType(ConversationType.P2P);
				long currentTimeMillis = System.currentTimeMillis();
				msgHist.setCreatedAt(currentTimeMillis);
				msgHist.setUpdatedAt(currentTimeMillis);
				msgHistRepository.saveMsgHist(msgHist);

				Message msgAck = msgAssembler.convertMgsHistToMessage(msgHist, ((Message) msg).getSeq());
				ctx.writeAndFlush(msgAck);

				Channel addresseeChannel = channelManager.findChannelByUid(msgHist.getReceiverId().toHexString());
				if (addresseeChannel != null) {
					MsgReadNotify msgReadNotify = msgAssembler.buildMsgReadNotify(msgHist);
					Message message = Message.newBuilder().setOpCode(OpCode.MSG_READ_NOTIFY)
							.setMsgReadNotify(msgReadNotify).build();
					addresseeChannel.writeAndFlush(message);
				}

			}
			else {
				ctx.fireChannelRead(msg);
			}
		}
		else {
			ctx.fireChannelRead(msg);
		}

		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.MSG_READ_ACK) {
			MsgHist msgHist = msgAssembler.convertMsgReadAckToMsgHist((Message) msg);
			msgHist.setMsgStatus(MsgStatus.READ);
			msgHistRepository.updateMsgStatusByMsgId(msgHist.getMsgId(), msgHist.getMsgStatus());
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
