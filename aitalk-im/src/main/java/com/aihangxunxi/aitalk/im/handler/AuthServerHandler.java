package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.AuthAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.constant.RedisKeyConstants;
import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.MsgReadNotify;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.GroupMemberRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import com.aihangxunxi.common.entity.LoginUserResponseRedisEntity;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
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

	@Resource
	private GroupMemberRepository groupMemberRepository;

	@Resource
	private RedisTemplate<String, Object> authRedisTemplate;

	@Resource
	private UserRepository userRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
		logger.debug(msg.toString());
		Channel currentChannel;
		Channel oldChannel;
		boolean result = false;
		String userId = "";
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.AUTH_REQUEST) {
			String token = ((Message) msg).getAuthRequest().getToken();
			String deviceCode = ((Message) msg).getAuthRequest().getDeviceCode();
			// todo:验证token合法性,并获取用户信息
			LoginUserResponseRedisEntity redisEntity = (LoginUserResponseRedisEntity) authRedisTemplate.opsForValue()
					.get(RedisKeyConstants.ACCESS_TOKEN + token);
			if (logger.isDebugEnabled()) {
				logger.debug(">>>> Redis 获取数据成功！{}", redisEntity);
			}
			if (redisEntity == null) {
				redisEntity = (LoginUserResponseRedisEntity) authRedisTemplate.opsForValue()
						.get(RedisKeyConstants.MIN_PROGRAM_ACCESS_TOKEN + token);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(">>>> Redis 获取数据成功！{}", redisEntity);
			}
			if (redisEntity != null) {
				User user = userRepository.getUserByUserId(Long.parseLong(redisEntity.getUserId()));
				if (user != null) {
					result = true;
					userId = user.getId().toHexString();
					currentChannel = this.channelProcess(ctx, user);
					oldChannel = channelManager.findChannelByUserId(userId);
					if (oldChannel != null) {
						if (!oldChannel.attr(ChannelConstant.DEVICE_CODE_ATTRIBUTE_KEY).get().toLowerCase().equals(
								ctx.channel().attr(ChannelConstant.DEVICE_CODE_ATTRIBUTE_KEY).get().toLowerCase())) {
							Message message = Message.newBuilder().setOpCode(OpCode.DISCONNECT_REQUEST).build();
							oldChannel.writeAndFlush(message);
						}
						oldChannel.attr(ChannelConstant.IS_OLD_CHANNEL_ATTRIBUTE_KEY).set(Boolean.TRUE);
						oldChannel.close();
					}

					// todo:后面考虑多设备登录的清空
					channelManager.addChannel(currentChannel);
					ctx.pipeline().remove(this);
					Message message = authAssembler.authAckBuilder(((Message) msg).getSeq(), userId, result);
					ctx.writeAndFlush(message);
				}
				else {
					Message message = authAssembler.authAckBuilder(((Message) msg).getSeq(), userId, result);
					ctx.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE);
				}
			}
			else {
				Message message = authAssembler.authAckBuilder(((Message) msg).getSeq(), userId, result);
				// ctx.writeAndFlush(message);
				ctx.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE);
			}
			// ctx.channel().id().asLongText()

			// todo 获取用户所在的群 并将用户的channel加入到groupManager
			// List<GroupMember> list =
			// groupMemberRepository.queryUsersGroup(Long.parseLong(redisEntity.getUserId()));
			// list.stream().forEach(groupMember -> {
			// groupManager.addChannel(groupMember.getGroupId().toString(),
			// ctx.channel());
			// });

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
		channel.attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).set(user.getId().toHexString());
		channel.attr(ChannelConstant.DEVICE_CODE_ATTRIBUTE_KEY).set(user.getDeviceCode());
		channel.attr(ChannelConstant.DEVICE_IDIOM_ATTRIBUTE_KEY).set(user.getDeviceIdiom());
		channel.attr(ChannelConstant.DEVICE_PLATFORM_ATTRIBUTE_KEY).set(user.getDevicePlatform());
		channel.attr(ChannelConstant.USER_NICKNAME_ATTRIBUTE_KEY).set(user.getNickname());
		channel.attr(ChannelConstant.USER_PROFILE_PHOTO_ATTRIBUTE_KEY).set(user.getHeader());
		channel.attr(ChannelConstant.IS_OLD_CHANNEL_ATTRIBUTE_KEY).set(Boolean.FALSE);
		return channel;
	}

}
