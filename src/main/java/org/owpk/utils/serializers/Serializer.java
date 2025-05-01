package org.owpk.utils.serializers;

public interface Serializer {
	<T> T convert(byte[] data, Class<T> clazz) throws SerializingException;

	<T> byte[] dump(T obj) throws SerializingException;
}