package com.aihangxunxi.aitalk.im.session;

import com.aihangxunxi.aitalk.im.protocol.constant.OpCode;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.collect.Sets;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/**
 * session本地存储
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/5/12
 */
@Component
public class MapSessionRepository implements SessionRepository<MapSession> {

	private static final Logger logger = LoggerFactory.getLogger(MapSessionRepository.class);

	// session 缓存Cache<sessionId,Session>
	private final Cache<String, Session> localSessionCache;

	// 用户多客户端session集合缓存Cache<userId, Set<sessionId>>
	private final Cache<Long, Set<String>> localUserSessionSetCache;

	public MapSessionRepository(Cache<String, Session> localSessionCache,
			Cache<Long, Set<String>> localUserSessionSetCache) {
		this.localSessionCache = localSessionCache;
		this.localUserSessionSetCache = localUserSessionSetCache;
	}

	@Override
	public MapSession createSession(ChannelHandlerContext context) {
		return new MapSession(context);
	}

	@Override
	public void save(MapSession session) {
		String sessionId = session.getId();
		Long userId = session.getRequiredAttribute(SessionConstant.USER_ID_KEY);
		// todo：剔除同平台用户
		// kickUser(session);
		localSessionCache.put(sessionId, session);
		Set<String> sessionIdSet = localUserSessionSetCache.getIfPresent(userId);
		sessionIdSet = Optional.ofNullable(sessionIdSet).map(sessionIdS -> {
			sessionIdS.add(sessionId);
			return sessionIdS;
		}).orElseGet(() -> Sets.newHashSet(sessionId));
		localUserSessionSetCache.put(userId, sessionIdSet);

	}

	/**
	 * 删除session
	 * @param ctx {@link ChannelHandlerContext}
	 */
	@Override
	public void delete(ChannelHandlerContext ctx) {
		String sessionId = ctx.channel().id().asLongText();
		this.deleteById(sessionId);

	}

	/**
	 * 剔除用户
	 * @param newSession 新接入用户seesion
	 */
	@Override
	public void kickUser(Session newSession) {
		Long userId = newSession.getRequiredAttribute(SessionConstant.USER_ID_KEY);
		PlatformType type = PlatformType.getOfValue(newSession.getAttribute(SessionConstant.PLATFORM_KEY));

		Set<String> sessionSet = this.findSessionSetByUserId(userId);
		if (sessionSet != null) {
			sessionSet.forEach((sessionId) -> {
				MapSession olderSession = this.findById(sessionId);
				PlatformType olderType = PlatformType
						.getOfValue(olderSession.getAttribute(SessionConstant.PLATFORM_KEY));
				switch (type) {
				case IOS:
				case ANDROID:
					// if (olderType == IOS || olderType == ANDROID) {
					// // todo: 剔除用户并发送剔除命令
					// Channel toChannel = (Channel)
					// olderSession.getAttribute(SessionConstant.CHANNEL_KEY);
					// toChannel.writeAndFlush(Message.newBuilder().setOpCode(OpCode.LOG_OUT.getValue()));
					//
					// this.deleteById(olderSession.getId());
					// }
					break;
				case WEB:
					// if (olderType == WEB) {
					// // todo: 剔除用户并发送剔除命令
					// Channel toChannel = (Channel)
					// olderSession.getAttribute(SessionConstant.CHANNEL_KEY);
					// toChannel.writeAndFlush(Message.newBuilder().setOpCode(OpCode.LOG_OUT.getValue()));
					//
					// this.deleteById(olderSession.getId());
					// }
					break;
				}
			});
		}
	}

	/**
	 * 根据session id 查询session
	 * @param sessionId {@link Session#getId()}
	 * @return Session {@link Session}
	 */
	@Override
	public MapSession findById(String sessionId) {

		return (MapSession) localSessionCache.getIfPresent(sessionId);
	}

	public Set<String> findSessionSetByUserId(Long userId) {
		return localUserSessionSetCache.getIfPresent(userId);
	}

	/**
	 * 从localSessionCache删除session
	 * @param sessionId {@link ChannelId#asLongText()} same as sessionId
	 */
	@Override
	public void deleteById(String sessionId) {
		Long userId;
		MapSession session = this.findById(sessionId);

		if (session != null) {
			userId = session.getRequiredAttribute(SessionConstant.USER_ID_KEY);
			// 清理 localUserSessionSetCache
			Set<String> sessionIdSet = findSessionSetByUserId(userId);
			if (sessionIdSet != null) {
				sessionIdSet.remove(sessionId);
				if (sessionIdSet.isEmpty()) {
					this.localUserSessionSetCache.invalidate(userId);
				}
			}
			// 清理 localSessionCache
			this.localSessionCache.invalidate(sessionId);
		}

	}

	/**
	 * session 缓存大小
	 * @return session 缓存大小
	 */
	public Long getLocalSessionCacheSize() {
		return this.localSessionCache.estimatedSize();
	}

	/**
	 * 用户多客户端session集合缓存 大小
	 * @return 用户多客户端session集合缓存 大小
	 */
	public Long getLocalUserSessionSetCache() {
		return this.localUserSessionSetCache.estimatedSize();
	}

}
