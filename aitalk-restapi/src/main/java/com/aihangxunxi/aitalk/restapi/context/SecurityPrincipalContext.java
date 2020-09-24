package com.aihangxunxi.aitalk.restapi.context;

/**
 * 用户信息上下文
 *
 * @author wangchaohao
 * @version 3.0 2020/4/13
 */
public class SecurityPrincipalContext {

	public static ThreadLocal<AihangPrincipal> context = new ThreadLocal<AihangPrincipal>();

	public static AihangPrincipal currentUser() {
		return context.get();
	}

	public static void set(AihangPrincipal aihangPrincipal) {
		context.set(aihangPrincipal);
	}

	public static void shutdown() {
		context.remove();
	}

}
