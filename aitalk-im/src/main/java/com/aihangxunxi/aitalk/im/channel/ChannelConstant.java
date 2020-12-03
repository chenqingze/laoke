package com.aihangxunxi.aitalk.im.channel;

import com.aihangxunxi.aitalk.storage.constant.DeviceIdiom;
import com.aihangxunxi.aitalk.storage.constant.DevicePlatform;
import io.netty.util.AttributeKey;
import org.w3c.dom.Attr;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
public final class ChannelConstant {

	private ChannelConstant() {
	}

	// 最大协议包长度
	public static final int MAX_FRAME_LENGTH = 1024 * 10; // 10k

	// 大文件
	public static final int MAX_AGGREGATED_CONTENT_LENGTH = 64 * 1024;

	private static final String USER_ID_KEY = "user_id";

	private static final String DEVICE_CODE_KEY = "device_code";

	private static final String DEVICE_IDIOM_KEY = "device_idiom";

	private static final String DEVICE_PLATFORM_KEY = "device_platform";

	private static final String USER_PROFILE_PHOTO_KEY = "profile_photo";

	private static final String USER_NICKNAME_KEY = "user_nickname";

	private static final String IS_OLD_CHANNEL_KEY = "is_old_channel";

	public static final AttributeKey<String> USER_ID_ATTRIBUTE_KEY = AttributeKey.newInstance(USER_ID_KEY);

	public static final AttributeKey<String> DEVICE_CODE_ATTRIBUTE_KEY = AttributeKey.newInstance(DEVICE_CODE_KEY);

	public static final AttributeKey<String> USER_PROFILE_PHOTO_ATTRIBUTE_KEY = AttributeKey
			.newInstance(USER_PROFILE_PHOTO_KEY);

	public static final AttributeKey<String> USER_NICKNAME_ATTRIBUTE_KEY = AttributeKey.newInstance(USER_NICKNAME_KEY);

	public static final AttributeKey<DeviceIdiom> DEVICE_IDIOM_ATTRIBUTE_KEY = AttributeKey
			.newInstance(DEVICE_IDIOM_KEY);

	public static final AttributeKey<DevicePlatform> DEVICE_PLATFORM_ATTRIBUTE_KEY = AttributeKey
			.newInstance(DEVICE_PLATFORM_KEY);

	public static final AttributeKey<Boolean> IS_OLD_CHANNEL_ATTRIBUTE_KEY = AttributeKey
			.newInstance(IS_OLD_CHANNEL_KEY);
}
