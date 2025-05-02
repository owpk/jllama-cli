package org.owpk.config.properties.provider;

import org.owpk.storage.Storage;
import org.owpk.utils.serializers.Serializer;
import org.owpk.utils.serializers.SerializingException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class RemoteObjectPropsProvider<T> extends AbstractObjectPropertiesProvider<T> {
	protected final String path;
	protected final Storage storage;

	public RemoteObjectPropsProvider(String propertiesFilePath, Storage storage, Class<T> cl, Serializer serializer) {
		super(serializer, cl);
		this.storage = storage;
		this.path = propertiesFilePath;
	}

	@Override
	public T load() throws PropertiesProccessingException {
		var data = storage.getContent(path);
		if (data == null) {
			throw createPropertiesProccessingException(new NullPointerException());
		}
		try {
			return serializer.convert(data, cl);
		} catch (SerializingException e) {
			throw createPropertiesProccessingException(e);
		}
	}

	@Override
	public void save(T value) throws PropertiesProccessingException {
		try {
			var data = serializer.dump(value);
			if (data == null)
				throw createPropertiesProccessingException(new NullPointerException());
			storage.saveContent(path, data, false);
			log.info("Properties saved to path: " + path);
		} catch (SerializingException e) {
			throw createPropertiesProccessingException(e);
		}
	}

	@Override
	public void onPropertiesChanged(T value) {
		log.info("Properties changed: " + value);
		save(value);
	}

	private PropertiesProccessingException createPropertiesProccessingException(Exception e) {
		return new PropertiesProccessingException("Failed to serialize properties to path: " + path, e);
	}
}