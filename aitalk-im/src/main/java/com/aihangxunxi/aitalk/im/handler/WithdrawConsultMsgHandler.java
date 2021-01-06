package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.RecevieWithdrawMsg;
import com.aihangxunxi.aitalk.im.protocol.buffers.WithdrawConsultAck;
import com.aihangxunxi.aitalk.im.utils.PushUtils;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.MsgHistRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
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

	@Resource
	private PushUtils pushUtils;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.WITHDRAW_CONSULT_REQUEST) {
			try {
				String msgId = ((Message) msg).getWithdrawConsultRequest().getMsgId();
				// 接受者ID
				ObjectId receiverId = new ObjectId(((Message) msg).getWithdrawConsultRequest().getConversationId());
				String consultDirection = ((Message) msg).getWithdrawConsultRequest().getConsultDirection();
				// 修改消息历史表
				msgHistRepository.withdrawConsultMsg(msgId);

				Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.WITHDRAW_CONSULT_ACK)
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
				Message recevie = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.RECEIVE_WITHDRAW_MSG)
						.setRecevieWithdrawMsg(RecevieWithdrawMsg.newBuilder().setMsgId(msgId)
								.setSenderId(user.getId().toHexString())
								.setConsultDirection(getConsultDirection(consultDirection)).setSuccess("ok").build())
						.build();

				if (addresseeChannel != null) {
					addresseeChannel.writeAndFlush(recevie);
				}

				// 查询离线消息表中是否存在，存在修改并存在则插入，（从消息历史表中获取）
				msgHistRepository.editOfflienMsg(msgId);

				String senderName = msgHistRepository.querySenderNicknameByMsgId(msgId);
				User device = msgHistRepository.querySenderDeviceByMsgId(msgId);
				System.out.println("开始进入推送");
				System.out.println(user.getDeviceCode());
				System.out.println(user.getDevicePlatform().name());

				// 获取发送者昵称
				if (device != null && device.getDeviceCode() != null && !device.getDeviceCode().isEmpty()) {
					System.out.println(senderName);
					pushUtils.pushMsg(senderName, "对方撤回了一条消息", device.getDeviceCode(),
							device.getDevicePlatform().name());
				}

			}
			finally {
				ReferenceCountUtil.release(msg);
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
