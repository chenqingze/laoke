package com.aihangxunxi.aitalk.im;

import com.aihangxunxi.aitalk.im.config.ImServerConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AitalkImApp {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		// ctx.register(AppConfig.class, OtherConfig.class);
		ctx.register(ImServerConfiguration.class);
		ctx.refresh();
	}

}
