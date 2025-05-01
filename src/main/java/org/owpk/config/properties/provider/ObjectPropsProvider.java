package org.owpk.config.properties.provider;

public interface ObjectPropsProvider<T> {

	void save(T value) throws PropertiesProccessingException;

	T load() throws PropertiesProccessingException;

	void onPropertiesChanged(T value);
}
