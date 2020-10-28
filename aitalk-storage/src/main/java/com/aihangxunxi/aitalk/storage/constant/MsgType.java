package com.aihangxunxi.aitalk.storage.constant;

/**
 * 消息内容格式类型
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public enum MsgType {

	TEXT, // 文本0

	AUDIO, // 语音1

	VIDEO, // 视频2

	LOCATION, // 位置3

	CARD, // 名片4

	IMAGE, // 图片5

	INFO, // 讯息6

	PURCHASE, // 团购7

	NOTIFICATION, // 通知消息8,

	STOREGOODS, // 商品(商铺)9

	BARTER, // 易货10

	STORE, // 商铺11

	ROB, // 抢单12

	GROUP_INVITATION, // 群聊邀请13

	GROUP_NOTIFICATION, // 群通知14

	FILE, // 文件",15

	REPLY, // 回复16

	INFROM, // 回复17

	AT, // @某人18

	PREORDER, // 预购单19

	DELIVERY, // 配送20

	DYNAMIC, // 动态 21

	DISCOUNTGOODS, // 折扣商品(自营) 22

	SECKILLGOODS, // 秒杀商品(自营) 23

	AVCHAT, // 音视频通话

	TIP, // 提醒消息

	ROBOT, // 机器人消息

	CUSTOM, // 自定义消息

	UNDEF; // UNKNOWN

	public static MsgType codeOf(int code) {
		for (MsgType msgType : values()) {
			if (msgType.ordinal() == code) {
				return msgType;
			}
		}
		throw new RuntimeException("没找到对应的枚举");
	}

}
