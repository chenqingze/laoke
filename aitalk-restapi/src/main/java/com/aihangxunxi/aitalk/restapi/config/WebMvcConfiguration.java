package com.aihangxunxi.aitalk.restapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Web MVC Context
 *
 * @author chenqingze1987@gmail.com
 * @version 1.0
 * @since 2020/6/18
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.aihangxunxi.aitalk.restapi")
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Value("${spring.profiles.active}")
	private String env;
//
//	@Resource
//	private RedisTemplate redisTemplate;

	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		// 设置允许跨域的路径
		registry.addMapping("/**")
				// 设置允许跨域请求的域名
				.allowedOrigins("*")
				// 是否允许证书
//				.allowCredentials(true)
				// 设置允许的方法
				.allowedMethods("GET", "POST", "DELETE", "PUT","OPTIONS")
				// 设置允许的header属性
				.allowedHeaders("*")
				// 跨域允许时间
				.maxAge(3600);
	}
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// 下面的值不设置，默认拦截全部路径
//		// 增加过滤路径
//		List<String> defaultIncludePatterns = new ArrayList<>();
//		defaultIncludePatterns.add("/**");
//		// 排除过滤路径
//		List<String> defaultExcludePatterns = new ArrayList<>();
//
//		// 放开全部接口
//		// defaultExcludePatterns.add("/**");
//		defaultExcludePatterns.add("/error");
//		defaultExcludePatterns.add("/v2/**");
//		defaultExcludePatterns.add("/group/save/user");
//		defaultExcludePatterns.add("/group/ok/test");
//		// defaultExcludePatterns.add("/user/telephone");
//		defaultExcludePatterns.add("/user/status");
//		defaultExcludePatterns.add("/user/phone");
//		defaultExcludePatterns.add("/system-info/sentSystemInfo/**");
//		// registry.addInterceptor(new
//		// AuthenticationInterceptor(redisTemplate)).addPathPatterns(defaultIncludePatterns)
//		// .excludePathPatterns(defaultExcludePatterns);
//	}

	// /**
	// * 设置api方法参数 AihangPrincipal
	// * @param argumentResolvers
	// */
	// @Override
	// public void addArgumentResolvers(List<HandlerMethodArgumentResolver>
	// argumentResolvers) {
	// argumentResolvers.add(currentUserHandlerMethodArgumentResolver());
	// }

	// @Bean
	// public HandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver() {
	// return new HandlerMethodArgumentResolver() {
	// @Override
	// public boolean supportsParameter(MethodParameter parameter) {
	// return parameter.getParameterType().equals(AihangPrincipal.class);
	// }
	//
	// @Override
	// public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer
	// mavContainer,
	// NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
	// try {
	// return SecurityPrincipalContext.currentUser();
	// }
	// catch (Exception e) {
	// return null;
	// }
	// }
	// };
	// }
	//
	// @Bean
	// public Banner banner() {
	// return new Banner(env);
	// }

}
