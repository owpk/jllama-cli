package org.owpk.config.properties;

import java.util.Map;
import java.util.WeakHashMap;

import org.owpk.config.properties.core.ApiKeyProps;
import org.owpk.config.properties.core.AppBaseProps;
import org.owpk.config.properties.validator.PropertyValidationException;
import org.owpk.storage.Storage;

public class BootstrapPropertiesFactory {
    private static final BootstrapPropertiesFactory INSTANCE = new BootstrapPropertiesFactory();

    private Map<Class<?>, AbsPropertiesProvider> providers = new WeakHashMap<>();

    public static BootstrapPropertiesFactory getInstance() {
        return INSTANCE;
    }

    private BootstrapPropertiesFactory() {
        registerProviders();
    }

    private void registerProviders() {
        var main = createDefaultMainPropertiesProvider();
        createCredentialPropertiesProvider(main);
    }

    public static void validateProviders() throws PropertyValidationException {
        for (var provider : INSTANCE.providers.values()) {
            provider.bootstrapValidation();
        }
    }

    public AbsPropertiesProvider getProvider(Class<?> providerClass) {
        return providers.get(providerClass);
    }

    private AppBaseProps creatMainPropsProvider(String name, Storage storage) {
        return (AppBaseProps) registerProvider(new AppBaseProps(name, storage));
    }

    private AppBaseProps createDefaultMainPropertiesProvider() {
        return (AppBaseProps) registerProvider(new AppBaseProps());
    }

    private ApiKeyProps createCredentialPropertiesProvider(AbsPropertiesProvider mainProps) {
        return (ApiKeyProps) registerProvider(new ApiKeyProps(mainProps.getStorage()));
    }

    private AbsPropertiesProvider registerProvider(AbsPropertiesProvider provider) {
        this.providers.put(provider.getClass(), provider);
        return provider;
    }

}
