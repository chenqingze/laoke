package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.MsgAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.constant.ConversationType;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import com.aihangxunxi.aitalk.storage.repository.MucHistRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 群聊
 */
@Component
@ChannelHandler.Sharable
public final class GroupMessageHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(GroupMessageHandler.class);

	@Resource
	private GroupManager groupManager;

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private MucHistRepository mucHistRepository;

	@Resource
	private MsgAssembler msgAssembler;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.MSG_REQUEST) {
			if (ConversationType.MUC.ordinal() == ((Message) msg).getMsgRequest().getConversationType().getNumber()) {
				try {

					String userObjectId = ctx.channel().attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get();

					logger.info("群消息");
					String conversationId = ((Message) msg).getMsgRequest().getConversationId();
					String senderId = ((Message) msg).getMsgRequest().getSenderId();
					Long seq = ((Message) msg).getSeq();
					if (groupRepository.checkUserInGroup(conversationId, Long.parseLong(senderId))) {
						// 1. 保存至数据库
						MucHist mucHist = msgAssembler.convertToMucHist((Message) msg);
						mucHistRepository.saveMucHist(mucHist);

						// 2. 转发给群内其他用户
						groupManager.sendGroupMsg(conversationId,
								msgAssembler.convertToMucRequest(mucHist, ((Message) msg).getSeq()));

						// 3. 发送回执 服务器已收到
						Message msgAck = msgAssembler.convertMucHistToMessage(mucHist, seq);
						ctx.writeAndFlush(msgAck);
					}
					else {
						MucHist mucHist = new MucHist();
						mucHist.setMsgId(null);
						Message msgAck = msgAssembler.convertMucHistToMessage(mucHist, seq);
						ctx.writeAndFlush(msgAck);
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
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
