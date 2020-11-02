package com.aihangxunxi.aitalk.storage.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.aihangxunxi.aitalk.storage.model.At;
import com.aihangxunxi.aitalk.storage.model.Groups;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.GroupMemberRepository;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 推送类
 *
 * @Author whc
 * @Datetime 2020.10.30
 */
@Component
public class PushUtils {

	private static final Logger logger = LoggerFactory.getLogger(PushUtils.class);

	@Resource
	private UserRepository userRepository;

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private GroupMemberRepository groupMemberRepository;

	// 极光相关配置
	private static String APP_KEY = "6a85934e7df54ac0fa45ff4b";

	// 极光相关设置
	private static String MASTER_SECRET = "ff067553661b490a34ddfb0b";

	/**
	 * 推送消息
	 * @param userId
	 * @param msgId
	 * @param msgContent
	 * @param msgType
	 * @param senderId
	 * @param mucId
	 */
	public void pushMsg(Long userId, String msgId, String msgContent, String msgType, Long senderId, String mucId)
			throws Exception {

		if (groupMemberRepository.checkMucMemberIsMute(userId, mucId)) {
			return;
		}

		// 根据用户id 获取推送设备和设备类型
		User receiverUser = userRepository.getUserByUserId(userId);

		String registrationId = receiverUser.getDeviceCode();
		String deviceType = receiverUser.getDevicePlatform().name();
		///////////////////////////////////////////
		// 爱航信息 //
		// 群名 //
		// 发送者名：发送内容 //
		///////////////////////////////////////////

		// 获取群名

		Groups groups = groupRepository.queryGroupInfo(mucId);
		String title = groups.getName();
		String senderName = groupMemberRepository.queryGroupMemberDisplayName(userId, senderId, mucId);

		String content = switchContent(msgType, msgContent, senderName);
		pushNotify(registrationId, deviceType, msgType, content, title, msgId, mucId);
	}

	// 根据类型返回推送内容
	private String switchContent(String msgType, String content, String senderName) throws JsonProcessingException {
		String msgBody = "";
		switch (msgType) {
		case "0":
		case "17":
			msgBody = content;
			break;
		case "1":
			msgBody = "[语音]";
			break;
		case "2":
			msgBody = "[视频]";
			break;
		case "3":
			msgBody = "[位置]";
			break;
		case "4":
			msgBody = "[名片]";
			break;
		case "5":
			msgBody = "[图片]";
			break;
		case "6":
			msgBody = "[讯息]";
			break;
		case "7":
			msgBody = "[团购]";
			break;
		case "8":
			msgBody = "[通知]";
			break;
		case "9":
			msgBody = "[商品]";
			break;
		case "10":
			msgBody = "[易货]";
			break;
		case "11":
			msgBody = "[店铺]";
			break;
		case "12":
			msgBody = "[抢单]";
			break;
		case "13":
			msgBody = "[群邀请]";
			break;
		case "14":
			msgBody = "[群通知]";
			break;
		case "15":
			msgBody = "[文件]";
			break;
		case "16":
			msgBody = "[相关通知]";
			break;
		case "18":
			ObjectMapper objectMapper = new ObjectMapper();
			At at = objectMapper.readValue(content, At.class);
			String users = "";
			for (int i = 0; i < at.getAtList().size(); i++) {
				users += ("@" + at.getAtList().get(i).getNickname());
			}
			msgBody = users + at.getContent();
			break;
		case "19":
			msgBody = "[预购单]";
			break;
		case "20":
			msgBody = "[配送]";
			break;
		case "21":
			msgBody = "[动态]";
			break;
		case "22":
			msgBody = "[秒杀商品]";
			break;
		case "23":
			msgBody = "[折扣商品]";
			break;
		}
		return senderName + ":" + msgBody;
	}

	public void pushNotify(String registrationId, String deviceType, String msgType, String msgBody, String title,
			String msgId, String groupId) {
		if ("android".equals(deviceType)) {
			JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
			Map<String, Map<String, String>> map = new HashMap<>();
			Map<String, String> secondary = new HashMap<>();
			secondary.put("distribution", "secondary_push");
			map.put("xiaomi", secondary);
			map.put("huawei", secondary);
			map.put("meizu", secondary);
			map.put("oppo", secondary);
			map.put("vivo", secondary);
			PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android())// 指定android平台的用户
					.setAudience(Audience.registrationId(registrationId))// registrationId指定用户
					.setNotification(Notification.newBuilder()
							.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).setAlert(msgBody)
									.setCategory("social").addExtra("msgId", msgId).addExtra("name", title)
									.addExtra("receiverType", "group").addExtra("content", msgBody)
									.addExtra("msgType", msgType).addExtra("groupId", groupId).setPriority(0)
									.setAlertType(7).build())
							.build())
					// 发送内容
					.setOptions(Options.newBuilder().setTimeToLive(60).setApnsProduction(true).setThirdPartyChannel(map)
							.build())
					// 这里是指定开发环境,不用设置也没关系
					.setMessage(cn.jpush.api.push.model.Message.content(msgBody))// 自定义信息
					.build();
			try {
				PushResult pu = jpushClient.sendPush(payload);

				if (pu.statusCode == 0) {
					logger.info("推送了一条群消息:msgId:{},title:{},body:{},groupId:{},registrationId:{}", msgId, title,
							msgBody, groupId, registrationId);
				}
				System.out.println(pu);
			}
			catch (APIConnectionException e) {
				e.printStackTrace();
			}
			catch (APIRequestException e) {
				e.printStackTrace();
			}
		}
		else {

			// IOS
			JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
			PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios())// ios平台的用户
					// .setAudience(Audience.all())//所有用户
					.setAudience(Audience.registrationId(registrationId))// registrationId指定用户
					.setNotification(
							Notification.newBuilder()
									.addPlatformNotification(IosNotification.newBuilder().setAlert(msgBody).setBadge(0)
											.setSound("default")// 这里是设置提示音(更多可以去官网看看)
											.addExtra("msgId", msgId).addExtra("name", title)
											.addExtra("receiverType", "group").addExtra("content", msgBody)
											.addExtra("msgType", msgType).addExtra("groupId", groupId).build())
									.build())
					// todo： 生产环境发版需要将.setApnsProduction(true)
					.setOptions(Options.newBuilder().setApnsProduction(false).setTimeToLive(60).build())
					.setMessage(
							cn.jpush.api.push.model.Message.newBuilder().setMsgContent(msgBody).setTitle(title).build())// 自定义信息
					.build();
			try {
				PushResult pu = jpushClient.sendPush(payload);
				logger.info("推送了一条群消息:msgId:{},title:{},body:{},groupId:{},registrationId:{}", msgId, title, msgBody,
						groupId, registrationId);

			}
			catch (APIConnectionException e) {
				e.printStackTrace();
			}
			catch (APIRequestException e) {
				e.printStackTrace();
			}
		}

	}

}
