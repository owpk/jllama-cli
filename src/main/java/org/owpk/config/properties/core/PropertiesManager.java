package org.owpk.config.properties.core;

import java.util.Map;
import java.util.WeakHashMap;

import org.owpk.config.properties.PropertiesProvider;
import org.owpk.config.properties.validator.PropertyValidationException;
import org.owpk.llm.ollama.client.OllamaProps;

public class PropertiesManager {
    private final Map<Class<?>, PropertiesProvider> providers = new WeakHashMap<>();

    public PropertiesManager() {
        registerProviders();
        validateProviders();
    }

    private void registerProviders() {
        var mainProps = createMainProps();
        createCredentialPropertiesProvider(mainProps);
        createOllamaProps(mainProps);
    }

    private void validateProviders() throws PropertyValidationException {
        for (var provider : providers.values()) {
            provider.bootstrapValidation();
        }
    }

    public PropertiesProvider getProvider(Class<?> providerClass) {
        return providers.get(providerClass);
    }

    private AppBaseProps createMainProps() {
        return (AppBaseProps) registerProvider(new AppBaseProps());
    }

    private PropertiesProvider createCredentialPropertiesProvider(AppBaseProps mainProps) {
        var storage = mainProps.getStorage();
        return registerProvider(new ApiKeyProps(storage));
    }

    private PropertiesProvider createOllamaProps(AppBaseProps mainProps) {
        var storage = mainProps.getStorage();
        return registerProvider(new OllamaProps(storage));
    }

    private PropertiesProvider registerProvider(PropertiesProvider provider) {
        this.providers.put(provider.getClass(), provider);
        return provider;
    }
}