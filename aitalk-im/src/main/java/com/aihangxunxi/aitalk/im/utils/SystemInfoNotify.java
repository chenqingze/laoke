package com.aihangxunxi.aitalk.im.utils;

import com.aihangxunxi.aitalk.im.assembler.MsgAssembler;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.SendSystemInfoRequest;
import com.aihangxunxi.aitalk.storage.model.SystemInfo;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SystemInfoNotify {

	private static final Logger logger = LoggerFactory.getLogger(SystemInfoNotify.class);

	@Resource
	private ChannelManager channelManager;

	@Resource
	private MsgAssembler msgAssembler;

	@Resource
	private PushUtils pushUtils;

	public boolean sendSystemNotify(String uId, SystemInfo systemInfo, User user) {
		logger.info(uId);
		Channel addresseeChannel = channelManager.findChannelByUserId(uId);
		if (addresseeChannel != null) {
			SendSystemInfoRequest sendSystemInfoRequest = msgAssembler.buildSendSystemInfoRequest(systemInfo);
			Message message = Message.newBuilder().setOpCode(OpCode.SYSTEM_INFO_NOTIFY)
					.setSendSystemInfoRequest(sendSystemInfoRequest).build();
			addresseeChannel.writeAndFlush(message);
		} else {
			this.pushUtils.pushMsg("爱航信息","[系统通知]"+ systemInfo.getContent(),user.getDeviceCode(),user.getDevicePlatform().toString());
		}
		return true;
	}

}
