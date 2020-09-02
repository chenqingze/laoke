package com.aihangxunxi.aitalk.im.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
// @Scope("prototype")
public final class Banner {

	private static final Logger logger = LoggerFactory.getLogger(Banner.class);

	public Banner(@Value("${env}") String env) {
		String foZu = "\n" + "                   _ooOoo_" + "\n" + "                  o8888888o" + "\n"
				+ "                  88\" . \"88" + "\n" + "                  (| -_- |)" + "\n"
				+ "                  O\\  =  /O" + "\n" + "               ____/`---'\\____" + "\n"
				+ "             .'  \\\\|     |//  `." + "\n" + "            /  \\\\|||  :  |||//  \\" + "\n"
				+ "           /  _||||| -:- |||||-  \\" + "\n" + "           |   | \\\\\\  -  /// |   |" + "\n"
				+ "           | \\_|  ''\\---/''  |   |" + "\n" + "           \\  .-\\__  `-`  ___/-. /" + "\n"
				+ "         ___`. .'  /--.--\\  `. . __" + "\n" + "      .\"\" '<  `.___\\_<|>_/___.'  >'\"\"." + "\n"
				+ "     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |" + "\n" + "     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /"
				+ "\n" + "======`-.____`-.___\\_____/___.-`____.-'======" + "\n" + "                   `=---='" + "\n"
				+ "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + "\n" + "          佛祖保佑  {}环境  永无BUG    ";
		logger.info(foZu, env);
	}

}
