package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.FriendAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.FriendAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.FriendProto;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.model.Friend;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.FriendRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@ChannelHandler.Sharable
public class FriendPullHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private ChannelManager channelManager;

	@Resource
	private UserRepository userRepository;

	@Resource
	private FriendRepository friendRepository;

	@Resource
	private FriendAssembler friendAssembler;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.FRIEND_REQUEST) {

			String userId = ctx.channel().attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get();

			User userById = userRepository.getUserById(new ObjectId(userId));
			List<Friend> frientList = friendRepository.getFrientList(userById.getUserId());

			Message message = friendAssembler.buildFriendAck(frientList);

			ctx.writeAndFlush(message);

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
