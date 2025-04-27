package org.owpk.config.properties;

import java.util.Properties;

import org.owpk.config.properties.validator.PropertyValidationException;
import org.owpk.storage.StorageException;

public interface PropertiesProvider {

    void setProperty(PropertyDef def, String value);

    String getProperty(String key);

    String getProperty(PropertyDef def);

    String findDefaultValueByKey(PropertyDef prop);

    Properties createDefaults() throws StorageException;

    void bootstrapValidation() throws PropertyValidationException;
}
