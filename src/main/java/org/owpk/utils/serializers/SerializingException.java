package org.owpk.utils.serializers;

public class SerializingException extends Exception {
	public SerializingException(String message) {
		super(message);
	}

	public SerializingException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerializingException(Throwable cause) {
		super(cause);
	}
}
