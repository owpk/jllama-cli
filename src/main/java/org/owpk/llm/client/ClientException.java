package org.owpk.llm.client;

public class ClientException extends RuntimeException {
	public ClientException(String message) {
		super(message);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

}
