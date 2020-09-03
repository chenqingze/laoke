package com.aihangxunxi.aitalk.im.session;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
public interface SessionRepository<S extends Session> {

	/**
	 * 创建并返回一个session {@link Session}
	 * @param context {@link ChannelHandlerContext}
	 * @return session
	 */
	S createSession(ChannelHandlerContext context);

	/**
	 * 保存session
	 * @param session 保存的seesion
	 */
	void save(S session);

	/**
	 * 删除session
	 * @param context {@link ChannelHandlerContext}
	 */
	void delete(ChannelHandlerContext context);

	/**
	 * 根据session id 查询session
	 * @param id session id {@link Session#getId()} same as channel id
	 * @return Session {@link Session}
	 */
	S findById(String id);

	/**
	 * 删除session
	 * @param id {@link ChannelId#asLongText()} same as sessionId
	 */
	void deleteById(String id);

	/**
	 * 剔除用户
	 * @param newSession 新接入session
	 */
	void kickUser(Session newSession);

}