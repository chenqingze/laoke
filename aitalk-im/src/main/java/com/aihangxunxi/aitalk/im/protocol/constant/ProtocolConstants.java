package com.aihangxunxi.aitalk.im.protocol.constant;

/**
 * 用于自定义协议封包解包，暂未用到
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/4/1
 */
public final class ProtocolConstants {

	public static final int PROTOCOL_HEADER_LENGTH = 18;// 默认消息头的长度

	public static final int PROTOCOL_VERSION = 1; // 协议版本号

	public static final int PROTOCOL_MAGIC = 0xCAFEBABE; // 魔数

	public static final char PROTOCOL_ERROR = '0';

	public static final char PROTOCOL_RESERVED = '0';

	private ProtocolConstants() {
	}

}
