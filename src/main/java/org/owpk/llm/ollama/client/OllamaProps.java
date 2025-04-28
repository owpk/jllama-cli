package org.owpk.llm.ollama.client;

import java.util.List;

import org.owpk.config.properties.AbsPropertiesProvider;
import org.owpk.config.properties.PropertyDef;
import org.owpk.config.properties.core.AppBaseProps;
import org.owpk.storage.RootedStorage;
import org.owpk.storage.Storage;
import org.owpk.storage.impl.LocalFileStorage;

import jakarta.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class OllamaProps extends AbsPropertiesProvider {
    public static final PropertyDef OLLAMA_API_URL = new PropertyDef("ollama.api.url", "http://localhost:11434");

    public OllamaProps(Storage storage) {
        super("ollama.properties", storage);
    }

    public OllamaProps() {
        this(new RootedStorage(AppBaseProps.DEF_GIGACHAT_CLI_HOME.value(), new LocalFileStorage()));
    }

    @Override
    public void bootstrapValidation() {
        if (checkIfNull(OLLAMA_API_URL)) {
            setProperty(OLLAMA_API_URL, findDefaultValueByKey(OLLAMA_API_URL));
        }
    }

    @Override
    protected List<PropertyDef> getDefaultProperties() {
        return List.of(OLLAMA_API_URL);
    }

    public String getApiUrl() {
        return getProperty(OLLAMA_API_URL);
    }

    public void setApiUrl(String url) {
        setProperty(OLLAMA_API_URL, url);
    }
}