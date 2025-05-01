package org.owpk.config.properties.model;

public record PropertyDef(String key, String value, String defaultValue) {
	public PropertyDef(String key, String value) {
		this(key, value, "");
	}

	public PropertyDef(String key) {
		this(key, "", "");
	}

}
