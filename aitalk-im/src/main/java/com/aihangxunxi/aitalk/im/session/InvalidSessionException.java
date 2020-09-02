package com.aihangxunxi.aitalk.im.session;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/30
 */
public class InvalidSessionException extends SessionException {

	public InvalidSessionException() {
	}

	public InvalidSessionException(String message) {
		super(message);
	}

	public InvalidSessionException(Throwable cause) {
		super(cause);
	}

	public InvalidSessionException(String message, Throwable cause) {
		super(message, cause);
	}

}
