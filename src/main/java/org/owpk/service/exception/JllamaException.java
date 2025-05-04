package org.owpk.service.exception;

public class JllamaException extends RuntimeException {
	public JllamaException(String message) {
		super(message);
	}

	public JllamaException(String message, Throwable cause) {
		super(message, cause);
	}

	public JllamaException(Throwable cause) {
		super(cause);
	}
}
