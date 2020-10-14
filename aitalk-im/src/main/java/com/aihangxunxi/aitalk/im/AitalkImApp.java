package com.aihangxunxi.aitalk.im;

import com.aihangxunxi.aitalk.im.config.ImServerConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 项目入口
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class AitalkImApp {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		// ctx.register(AppConfig.class, OtherConfig.class);
		ctx.register(ImServerConfiguration.class);
		ctx.refresh();
	}

}
