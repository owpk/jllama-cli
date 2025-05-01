package org.owpk.utils;

public interface Serializer {
	<T> T convert(byte[] data, Class<T> clazz);

	<T> byte[] dump(T obj);
}