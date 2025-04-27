package org.owpk.config.properties.core;

import java.util.Map;
import java.util.WeakHashMap;

import org.owpk.config.properties.PropertiesProvider;
import org.owpk.config.properties.validator.PropertyValidationException;

public class PropertiesManager {
    private static final PropertiesManager INSTANCE = new PropertiesManager();

    private final Map<Class<?>, PropertiesProvider> providers = new WeakHashMap<>();

    public static PropertiesManager getInstance() {
        return INSTANCE;
    }

    private PropertiesManager() {
        registerProviders();
    }

    private void registerProviders() {
        var mainProps = createMainProps();
        createCredentialPropertiesProvider(mainProps);
    }

    public static void validateProviders() throws PropertyValidationException {
        for (var provider : INSTANCE.providers.values()) {
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

    private PropertiesProvider registerProvider(PropertiesProvider provider) {
        this.providers.put(provider.getClass(), provider);
        return provider;
    }
}