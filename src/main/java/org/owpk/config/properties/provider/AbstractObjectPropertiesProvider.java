package org.owpk.config.properties.provider;

import org.owpk.utils.serializers.Serializer;

public abstract class AbstractObjectPropertiesProvider<T> implements ObjectPropsProvider<T> {
	protected final Serializer serializer;
	protected Class<T> cl;

	public AbstractObjectPropertiesProvider(Serializer serializer, Class<T> cl) {
		this.serializer = serializer;
		this.cl = cl;
	}
}
