package com.aihangxunxi.aitalk.im.protocol.constant;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * sequence 生成器
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/4/1
 */
public class AtomicSequenceGenerator {

	private AtomicSequenceGenerator() {
	}

	private static final AtomicInteger value = new AtomicInteger(1);

	public static int getNext() {
		return value.getAndIncrement();
	}

}
