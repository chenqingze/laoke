package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.MsgAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.MsgReadNotify;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.constant.ConsultDirection;
import com.aihangxunxi.aitalk.storage.constant.ConversationType;
import com.aihangxunxi.aitalk.storage.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.model.At;
import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.MsgHistRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.aihangxunxi.aitalk.storage.constant.ConsultDirection.*;

/**
 * @Author guoyongsheng Data: 2020/11/2
 * @Version 3.0
 */
@Component
@ChannelHandler.Sharable
public class ConsultChatHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(P2PChatHandler.class);

	@Resource
	private ChannelManager channelManager;

	@Resource
	private MsgHistRepository msgHistRepository;

	@Resource
	private UserRepository userRepository;

	@Resource
	private MsgAssembler msgAssembler;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.MSG_REQUEST) {
			if (ConversationType.CONSULT.ordinal() == ((Message) msg).getMsgRequest().getConversationType()
					.getNumber()) {
				String userObjectId = ctx.channel().attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get();

				User user = userRepository.getUserById(new ObjectId(userObjectId));

				MsgHist msgHist = msgAssembler.convertMsgRequestToMsgHist((Message) msg);
				msgHist.setSenderId(user.getId());
				msgHist.setMsgStatus(MsgStatus.SUCCESS);
				msgHist.setConversationType(ConversationType.CONSULT);
				long currentTimeMillis = System.currentTimeMillis();
				msgHist.setCreatedAt(currentTimeMillis);
				msgHist.setUpdatedAt(currentTimeMillis);
				msgHistRepository.saveMsgHist(msgHist);

				Message msgAck = msgAssembler.convertMgsHistToMessage(msgHist, ((Message) msg).getSeq());
				ctx.writeAndFlush(msgAck);

				// 发送给被咨询者 咨询方向进行反转
				msgHist.setConsultDirection(getConsultDirection(msgHist.getConsultDirection()));
				Channel addresseeChannel = channelManager.findChannelByUserId(msgHist.getReceiverId().toHexString());
				if (addresseeChannel != null) {
					MsgReadNotify msgReadNotify = msgAssembler.buildMsgReadNotify(msgHist);
					Message message = Message.newBuilder().setOpCode(OpCode.CONSULT_MSG_READ_NOTIFY)
							.setMsgReadNotify(msgReadNotify).build();
					addresseeChannel.writeAndFlush(message);
				}
				else {
					// todo: 如果对方不在 处理
				}

			}
			else {
				ctx.fireChannelRead(msg);
			}
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

	// 反转咨询方向
	private ConsultDirection getConsultDirection(ConsultDirection consultDirection) {
		ConsultDirection consultDirectionR;
		switch (consultDirection) {
		case PSO:
			consultDirectionR = ConsultDirection.SPI;
			break;
		case PPO:
			consultDirectionR = ConsultDirection.PPI;
			break;
		case SPO:
			consultDirectionR = ConsultDirection.PSI;
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + consultDirection);
		}
		return consultDirectionR;
	}

}
