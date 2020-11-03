package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class BindUserDeviceHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private UserRepository userRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.BIND_USER_DEVICE_REQUEST) {

			String userId = ctx.channel().attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get();

			String deviceCode = ((Message) msg).getBindUserDeviceRequest().getDeviceCode();
			String deviceType = ((Message) msg).getBindUserDeviceRequest().getDeviceType();
			String deviceIdiom = ((Message) msg).getBindUserDeviceRequest().getDeviceIdiom();

			User user = userRepository.getUserById(new ObjectId(userId));
			this.userRepository.updateDeviceInfo(user.getUserId(), deviceCode, deviceType);
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
