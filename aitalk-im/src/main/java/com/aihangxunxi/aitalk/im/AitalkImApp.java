package com.aihangxunxi.aitalk.im;

import com.aihangxunxi.aitalk.im.config.ImServerConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AitalkImApp {

	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(ImServerConfiguration.class);
	}

}
