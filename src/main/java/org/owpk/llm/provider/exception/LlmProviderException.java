package org.owpk.llm.provider.exception;

public class LlmProviderException extends RuntimeException {
	public LlmProviderException(String message) {
		super(message);
	}

	public LlmProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	public LlmProviderException(Throwable cause) {
		super(cause);
	}

}
