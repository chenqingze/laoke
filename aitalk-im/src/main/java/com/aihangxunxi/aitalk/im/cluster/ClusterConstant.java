package com.aihangxunxi.aitalk.im.cluster;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/27 2:39 PM
 */
public class ClusterConstant {

	public static final String EXCHANGE_NAME = "msg_router";

	public static final String REDIS_USER_ID_PREFIX = "USER_ID@";

	public static final String REDIS_NODE_PREFIX = "NODE@";

	private ClusterConstant() {
	}

	public static String redisImNode() {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			return String.join("-", inetAddress.getHostName(), inetAddress.getHostAddress());
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
			// todo: 抛出自定义异常处理
			return null;
		}
	}

}
