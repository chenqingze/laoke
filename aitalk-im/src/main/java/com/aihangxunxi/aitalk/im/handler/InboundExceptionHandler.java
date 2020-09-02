package com.aihangxunxi.aitalk.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 入站异常处理
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/25
 */
public class InboundExceptionHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

}
