package org.owpk.config.properties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.owpk.config.properties.validator.PropertyValidationException;
import org.owpk.storage.Storage;
import org.owpk.storage.StorageException;

import lombok.Getter;

@Getter
public abstract class AbsPropertiesProvider implements PropertiesProvider {
    protected final String propertiesName;
    protected final Storage storage;
    protected final Properties properties;

    public AbsPropertiesProvider(String propertyName, Storage storage) {
        this.storage = storage;
        this.propertiesName = propertyName;

        if (!storage.exists(propertyName)) {
            storage.createFileOrDirIfNotExists(propertyName);
            this.properties = createDefaults();
        } else {
            this.properties = loadProperties(propertyName);
        }
    }

    public abstract void bootstrapValidation() throws PropertyValidationException;

    protected abstract List<PropertyDef> getDefaultProperties();

    @Override
    public Properties createDefaults() throws StorageException {
        var defaultProperties = getDefaultProperties();

        var defaultProps = new Properties();
        for (var def : defaultProperties)
            defaultProps.setProperty(def.key(), def.value());

        this.storeProperties(defaultProps);
        return defaultProps;
    }

    public synchronized void setProperty(PropertyDef def, String value) {
        properties.setProperty(def.key(), value);
        storeProperties(properties);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(PropertyDef def) {
        return properties.getProperty(def.key());
    }

    public String findDefaultValueByKey(PropertyDef prop) {
        return getDefaultProperties().stream()
                .collect(Collectors.toMap(PropertyDef::key, PropertyDef::value))
                .get(prop.key());
    }

    private Properties loadProperties(String path) {
        var data = storage.getContent(path);
        Properties properties = new Properties();
        try (var byteArrayInputStream = new ByteArrayInputStream(data)) {
            properties.load(byteArrayInputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties: " + path, e);
        }
    }

    public void storeProperties(Properties properties) {
        var buff = new ByteArrayOutputStream();
        try {
            properties.store(buff, "https://developers.sber.ru/docs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        storage.saveContent(propertiesName, buff.toString().getBytes(StandardCharsets.UTF_8), false);
    }

    protected boolean checkIfNull(PropertyDef def) {
        var value = getProperty(def);
        return value == null || value.isEmpty();
    }

    protected void acceptEmpty(PropertyDef def, Consumer<PropertyDef> consumer) {
        if (checkIfNull(def))
            consumer.accept(def);
    }

    protected void throwIfEmpty(List<PropertyDef> properties) throws PropertyValidationException {
        properties.forEach(it -> {
            throwIfEmpty(it);
        });
    }

    protected void throwIfEmpty(PropertyDef def) {
        acceptEmpty(def, empty -> {
            throw new PropertyValidationException(empty, "is empty");
        });
    }

}