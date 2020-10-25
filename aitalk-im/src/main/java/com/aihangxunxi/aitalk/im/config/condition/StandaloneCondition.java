package com.aihangxunxi.aitalk.im.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/25 2:55 PM
 */
public class StandaloneCondition implements Condition {

	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		String serverModel = conditionContext.getEnvironment().getProperty("server.model");
		return serverModel.equalsIgnoreCase("standalone");
	}

}
