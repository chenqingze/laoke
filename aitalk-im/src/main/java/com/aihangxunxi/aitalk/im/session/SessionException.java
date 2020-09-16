package com.aihangxunxi.aitalk.im.session;

import com.aihangxunxi.aitalk.im.exception.AitalkException;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @deprecated session exception
 */
public class SessionException extends AitalkException {

    public SessionException() {
    }

    public SessionException(String message) {
        super(message);
    }

    public SessionException(Throwable cause) {
        super(cause);
    }

    public SessionException(String message, Throwable cause) {
        super(message, cause);
    }

}
