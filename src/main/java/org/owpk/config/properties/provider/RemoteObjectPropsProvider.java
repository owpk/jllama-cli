package org.owpk.config.properties.provider;

import org.owpk.storage.Storage;
import org.owpk.utils.Serializer;

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
			throw new PropertiesProccessingException("Failed to load properties from path: " + path);
		}
		return serializer.convert(data, cl);
	}

	@Override
	public void save(T value) throws PropertiesProccessingException {
		var data = serializer.dump(value);
		if (data == null) {
			throw new PropertiesProccessingException("Failed to serialize properties to path: " + path);
		}
		storage.saveContent(path, data, false);
	}

	@Override
	public void onPropertiesChanged(T value) {
		log.info("Properties changed: {}", value);
		try {
			save(value);
		} catch (PropertiesProccessingException e) {
			log.error("Failed to save properties after change: {}", e.getMessage());
		}
	}
}