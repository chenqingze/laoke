package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.UpdateUserProfileAck;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 更新用户信息
 */
@Component
@ChannelHandler.Sharable
public class UpdateUserHeaderHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private UserRepository userRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.UPDATE_USER_PROFILE_REQUEST) {
			String nickname = ((Message) msg).getUpdateUserProfileRequest().getNickname();
			String header = ((Message) msg).getUpdateUserProfileRequest().getProfileHeader();
			String userId = ctx.channel().attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get();

			userRepository.updateUserProfile(userId, header, nickname);

			Message ack = Message.newBuilder().setUpdateUserProfileAck(
					UpdateUserProfileAck.newBuilder().setUserId(userId).setMessage("修改成功").setSuccess("ok").build())
					.build();

			ctx.writeAndFlush(ack);
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
