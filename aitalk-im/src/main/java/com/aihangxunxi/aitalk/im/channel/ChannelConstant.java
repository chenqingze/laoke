package com.aihangxunxi.aitalk.im.channel;

import com.aihangxunxi.aitalk.storage.constant.DeviceIdiom;
import com.aihangxunxi.aitalk.storage.constant.DevicePlatform;
import io.netty.util.AttributeKey;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
public final class ChannelConstant {

	private ChannelConstant() {
	}

	private static final String USER_ID_KEY = "user_id";

	private static final String DEVICE_CODE_KEY = "device_code";

	private static final String DEVICE_IDIOM_KEY = "device_idiom";

	private static final String DEVICE_PLATFORM_KEY = "device_platform";

	public static final AttributeKey<String> USER_ID_ATTRIBUTE_KEY = AttributeKey.newInstance(USER_ID_KEY);

	public static final AttributeKey<String> DEVICE_CODE_ATTRIBUTE_KEY = AttributeKey.newInstance(DEVICE_CODE_KEY);

	public static final AttributeKey<DeviceIdiom> DEVICE_IDIOM_ATTRIBUTE_KEY = AttributeKey
			.newInstance(DEVICE_IDIOM_KEY);

	public static final AttributeKey<DevicePlatform> DEVICE_PLATFORM_ATTRIBUTE_KEY = AttributeKey
			.newInstance(DEVICE_PLATFORM_KEY);

}
