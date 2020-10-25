package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.AuthAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.model.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 建立连接登录/认证操作
 *
 * @author chenqingze1987@gmail.com
 * @version 1.0
 * @since 2020/4/1
 */
@Component
@ChannelHandler.Sharable
public final class AuthServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthServerHandler.class);

	@Resource
	private ChannelManager channelManager;

	@Resource
	private AuthAssembler authAssembler;

	@Resource
	private GroupManager groupManager;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.debug(msg.toString());
		boolean result = false;
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.AUTH_REQUEST) {
			String token = ((Message) msg).getAuthRequest().getToken();
			// todo:验证token合法性,并获取用户信息

			String uid = "5f687cece62333c82038e17b";
			if ("456".equals(token.trim()))
				uid = "5f687cece62333c82038e17c";
			if ("789".equals(token.trim()))
				uid = "5f687cece62333c82038e17d";

			User user = new User();
			user.setUid(new ObjectId(uid));
			user.setUserId(Long.valueOf(token));
			if (true) {
				result = true;
				channelManager.addChannel(this.channelProcess(ctx, user));
				ctx.pipeline().remove(this);
			}
			// todo 获取用户所在的群 并将用户的channel加入到groupManager
			// for(){
			// groupManager.addChannel(groupId,ctx.channel);
			// }
			Message message = authAssembler.authAckBuilder(((Message) msg).getSeq(), ctx.channel().id().asLongText(),
					result);
			ctx.writeAndFlush(message);
		}
		else {
			ctx.close();
			// ctx.fireChannelRead(msg);
		}
	}

	/**
	 * todo 完善
	 * @param ctx
	 * @param user
	 * @return
	 */
	private Channel channelProcess(ChannelHandlerContext ctx, User user) {
		Channel channel = ctx.channel();
		channel.attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).set(user.getUid().toHexString());
		channel.attr(ChannelConstant.DEVICE_CODE_ATTRIBUTE_KEY).set(user.getDeviceCode());
		channel.attr(ChannelConstant.DEVICE_IDIOM_ATTRIBUTE_KEY).set(user.getDeviceIdiom());
		channel.attr(ChannelConstant.DEVICE_PLATFORM_ATTRIBUTE_KEY).set(user.getDevicePlatform());
		return channel;
	}

}
