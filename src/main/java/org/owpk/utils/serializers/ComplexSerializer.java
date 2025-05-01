package org.owpk.utils.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;

public interface ComplexSerializer extends Serializer {
	<T> T convert(byte[] data, JavaType clazz) throws SerializingException;

	<T> T convert(byte[] data, TypeReference<T> clazz) throws SerializingException;
}
