package com.aihangxunxi.aitalk.im.utils;

import com.aihangxunxi.aitalk.im.assembler.MsgAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.SendSystemInfoRequest;
import com.aihangxunxi.aitalk.storage.constant.DevicePlatform;
import com.aihangxunxi.aitalk.storage.model.SystemInfo;
import com.aihangxunxi.aitalk.storage.model.User;
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
			logger.info("在线");
			SendSystemInfoRequest sendSystemInfoRequest = msgAssembler.buildSendSystemInfoRequest(systemInfo);
			Message message = Message.newBuilder().setOpCode(OpCode.SYSTEM_INFO_NOTIFY)
					.setSendSystemInfoRequest(sendSystemInfoRequest).build();
			addresseeChannel.writeAndFlush(message);
			// ios 是否后天运行  发送极光推送
			if (user.isBackground()) {
				if (DevicePlatform.IOS.equals(user.getDevicePlatform())) {
					logger.info("在线 并且是ios 发送极光");
					this.pushUtils.pushMsg("爱航信息", "[系统通知]" + systemInfo.getContent(), user.getDeviceCode(),
							user.getDevicePlatform().toString(), null);
				}
			}
		}
		else {
			logger.info("不在线");
			this.pushUtils.pushMsg("爱航信息", "[系统通知]" + systemInfo.getContent(), user.getDeviceCode(),
					user.getDevicePlatform().toString(), null);
		}
		return true;
	}

}
