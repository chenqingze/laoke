package com.aihangxunxi.aitalk.restapi.config;

import com.aihangxunxi.aitalk.restapi.context.AihangPrincipal;
import com.aihangxunxi.aitalk.restapi.context.SecurityPrincipalContext;
import com.aihangxunxi.aitalk.restapi.interceptor.AuthenticationInterceptor;
import com.aihangxunxi.aitalk.storage.config.StorageConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@Import(StorageConfiguration.class)
@ComponentScan(basePackages = "com.aihangxunxi.aitalk.restapi")
public class WebMvcConfiguration implements WebMvcConfigurer {


	@Resource
	private AuthenticationInterceptor authenticationInterceptor;

	// todo 注释掉
	 @Override
	 public void addCorsMappings(CorsRegistry registry) {
	 // 设置允许跨域的路径
	 registry.addMapping("/**")
	 // 设置允许跨域请求的域名
	 .allowedOrigins("*")
	 // 是否允许证书
	 // .allowCredentials(true)
	 // 设置允许的方法
	 .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
	 // 设置允许的header属性
	 .allowedHeaders("*")
	 // 跨域允许时间
	 .maxAge(3600);
	 }


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 下面的值不设置，默认拦截全部路径
		// 增加过滤路径
		List<String> defaultIncludePatterns = new ArrayList<>();
		defaultIncludePatterns.add("/**");
		// 排除过滤路径
		List<String> defaultExcludePatterns = new ArrayList<>();

		// 放开全部接口
		// defaultExcludePatterns.add("/**");
		defaultExcludePatterns.add("/error/**");
		// defaultExcludePatterns.add("/swagger-resources/**");
		// defaultExcludePatterns.add("/doc.html");
		// defaultExcludePatterns.add("/swagger-ui.html/**");
		registry.addInterceptor(authenticationInterceptor).addPathPatterns(defaultIncludePatterns)
				.excludePathPatterns(defaultExcludePatterns);
	}

	/**
	 * 设置api方法参数 AihangPrincipal
	 * @param argumentResolvers
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserHandlerMethodArgumentResolver());
	}

	@Bean
	public HandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver() {
		return new HandlerMethodArgumentResolver() {
			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				return parameter.getParameterType().equals(AihangPrincipal.class);
			}

			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
				try {
					return SecurityPrincipalContext.currentUser();
				}
				catch (Exception e) {
					return null;
				}
			}
		};
	}

	/**
	 * 配置消息转换器 Long转String
	 * @param converters
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter httpMessageConverter = null;
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				httpMessageConverter = (MappingJackson2HttpMessageConverter) converter;

				ObjectMapper objectMapper = httpMessageConverter.getObjectMapper();
				// objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN,
				// true);
				// objectMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
				// objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				// false);
				// objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
				SimpleModule simpleModule = new SimpleModule();
				simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
				simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
				simpleModule.addSerializer(long.class, ToStringSerializer.instance);
				objectMapper.registerModule(simpleModule);

			}
		}
	}

	/**
	 * 获取请求client的ip
	 * @param request
	 * @return
	 */
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
