package com.boilerplate.demo.model.common;

public class SlackMessengerException extends RuntimeException {

	private static final long serialVersionUID = 8662519836551498617L;

	public SlackMessengerException() {
		super();
	}

	public SlackMessengerException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SlackMessengerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SlackMessengerException(String message) {
		super(message);
	}

	public SlackMessengerException(Throwable cause) {
		super(cause);
	}

}
