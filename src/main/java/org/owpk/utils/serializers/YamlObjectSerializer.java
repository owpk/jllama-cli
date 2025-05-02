package org.owpk.utils.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class YamlObjectSerializer implements ComplexSerializer {
	private final ObjectMapper objectMapper;
	{
		YAMLFactory yf = new YAMLFactory();
		yf.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER); // отключаем '---'
		objectMapper = new ObjectMapper(yf);
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Override
	public <T> T convert(byte[] data, Class<T> clazz) throws SerializingException {
		try {
			return objectMapper.readValue(data, clazz);
		} catch (Exception e) {
			throw new SerializingException("Failed to convert data to " + clazz.getName(), e);
		}
	}

	@Override
	public <T> byte[] dump(T obj) throws SerializingException {
		try {
			return objectMapper.writeValueAsBytes(obj);
		} catch (Exception e) {
			throw new SerializingException("Failed to dump object of type " + obj.getClass().getName(), e);
		}
	}

	@Override
	public <T> T convert(byte[] data, JavaType clazz) throws SerializingException {
		try {
			return objectMapper.readValue(data, clazz);
		} catch (Exception e) {
			throw new SerializingException("Failed to convert data to " + clazz.toString(), e);
		}
	}

	@Override
	public <T> T convert(byte[] data, TypeReference<T> clazz) throws SerializingException {
		try {
			return objectMapper.readValue(data, clazz);
		} catch (Exception e) {
			throw new SerializingException("Failed to convert data to " + clazz.getType().toString(), e);
		}
	}
}
