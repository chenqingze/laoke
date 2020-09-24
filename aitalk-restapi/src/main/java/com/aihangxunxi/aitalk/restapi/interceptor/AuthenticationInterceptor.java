package com.aihangxunxi.aitalk.restapi.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截用户请求，验证用户token的有效性
 *
 * @author wangchaochao
 * @version 3.0 2020/4/13
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	private RedisTemplate redisTemplate;

	public AuthenticationInterceptor(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 用户信息拦截,token校验。
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		try {
			// if (logger.isDebugEnabled()) {
			// logger.debug("Web client IP:{}", this.getClientIp(request));
			// }
			// String consumerFilter = request.getHeader("consumerFilter");
			// if ("yes".equals(consumerFilter)) {
			// return true;
			// }
			// if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
			// response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			// return true;
			// }
			// feign跨服调用白名单url不拦截
			// AntPathMatcher antPathMatcher = new AntPathMatcher();
			// Iterator<String> iterator = feignExcludePatterns.iterator();
			// while (iterator.hasNext()) {
			// if (antPathMatcher.match(iterator.next(), request.getRequestURI())) {
			// return true;
			// }
			// }
			// String token = request.getHeader("Authorization");
			// LoginUserResponseRedisEntity redisEntity = (LoginUserResponseRedisEntity)
			// redisTemplate.opsForValue()
			// .get(RedisKeyConstants.ACCESS_TOKEN + token);
			// if (redisEntity == null) {
			// redisEntity = (LoginUserResponseRedisEntity) redisTemplate.opsForValue()
			// .get(RedisKeyConstants.MIN_PROGRAM_ACCESS_TOKEN + token);
			// }
			// if (redisEntity == null) {
			// redisEntity = (LoginUserResponseRedisEntity) redisTemplate.opsForValue()
			// .get(RedisKeyConstants.H5_ACCESS_TOKEN + token);
			// }
			// if (redisEntity == null) {
			// response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token已失效");
			// logger.debug("=========》访问受限，token已失效");
			// return false;
			// }
			//
			// // 获取token信息
			// String userId = redisEntity.getUserId();
			// String userName = redisEntity.getUserName();
			// String userType = redisEntity.getUserType().getCode();
			//
			// AihangPrincipal aihangPrincipal = new AihangPrincipal();
			// aihangPrincipal.setUserId(Long.parseLong(userId));
			// aihangPrincipal.setUserName(userName);
			// aihangPrincipal.setUserType(userType);
			// aihangPrincipal.setToken(token);
			// SecurityPrincipalContext.set(aihangPrincipal);
		}
		catch (Exception ex) {
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "访问受限");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			logger.debug("=========》访问受限", ex);
			return false;
		}
		return true;

	}

	// @Override
	// public void afterCompletion(HttpServletRequest request, HttpServletResponse
	// response, Object arg2, Exception arg3) {
	// SecurityPrincipalContextpalContext.shutdown();
	// }

	private String getClientIp(HttpServletRequest request) {

		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}

}
