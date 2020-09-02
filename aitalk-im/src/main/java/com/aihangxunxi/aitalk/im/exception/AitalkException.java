package com.aihangxunxi.aitalk.im.exception;

/**
 * Root exception for all AiTalk runtime exceptions.
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/30
 */
public class AitalkException extends RuntimeException {

	public AitalkException() {
	}

	public AitalkException(String message) {
		super(message);
	}

	public AitalkException(Throwable cause) {
		super(cause);
	}

	public AitalkException(String message, Throwable cause) {
		super(message, cause);
	}

}
