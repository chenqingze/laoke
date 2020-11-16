package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.*;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.MsgHistRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 撤回咨询消息
 *
 * @Author guoyongsheng Data: 2020/11/4
 * @Version 3.0
 */
@Component
@ChannelHandler.Sharable
public class WithdrawConsultMsgHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private ChannelManager channelManager;

	@Resource
	private MsgHistRepository msgHistRepository;

	@Resource
	private UserRepository userRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.WITHDRAW_CONSULT_REQUEST) {
			String msgId = ((Message) msg).getWithdrawConsultRequest().getMsgId();
			// 接受者ID
			ObjectId receiverId = new ObjectId(((Message) msg).getWithdrawConsultRequest().getConversationId());
			String consultDirection = ((Message) msg).getWithdrawConsultRequest().getConsultDirection();
			msgHistRepository.withdrawConsultMsg(msgId);

			Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq()).setOpCode(OpCode.WITHDRAW_CONSULT_ACK)
					.setWithdrawConsultAck(
							WithdrawConsultAck.newBuilder().setMsgId(msgId).setConversationId(receiverId.toString())
									.setConsultDirection(consultDirection).setSuccess("ok").build())
					.build();
			ctx.writeAndFlush(ack);

			// 发送给被咨询者 咨询方向进行反转
			Channel addresseeChannel = channelManager.findChannelByUserId(receiverId.toHexString());
			String userObjectId = ctx.channel().attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get();
			User user = userRepository.getUserById(new ObjectId(userObjectId));

			// 发送对方请求
			Message request = Message.newBuilder().setSeq(((Message) msg).getSeq())
					.setOpCode(OpCode.WITHDRAW_CONSULT_REQUEST)
					.setWithdrawConsultRequest(WithdrawConsultRequest.newBuilder().setMsgId(msgId)
							.setConversationId(user.getId().toHexString())
							.setConsultDirection(getConsultDirection(consultDirection)).setSuccess("ok").build())
					.build();
			if (addresseeChannel != null) {
				addresseeChannel.writeAndFlush(request);
			}
			else {
				// todo:撤回的时候 如果对方不在线
				// 查询离线消息 将离线消息改为对方撤回了一条消息
				msgHistRepository.withdrawConsultOfflienMsg(msgId);
			}
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

	// 反转咨询方向
	private String getConsultDirection(String consultDirection) {
		String consultDirectionR;
		switch (consultDirection) {
		case "PSO":
			consultDirectionR = "SPI";
			break;
		case "PPO":
			consultDirectionR = "PPI";
			break;
		case "SPO":
			consultDirectionR = "PSI";
			break;
		default:
			throw new IllegalStateException("UnConsultDirection value: " + consultDirection);
		}
		return consultDirectionR;
	}

}
