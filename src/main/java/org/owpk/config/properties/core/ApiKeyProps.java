package org.owpk.config.properties.core;

import java.util.List;

import org.owpk.config.properties.AbsPropertiesProvider;
import org.owpk.config.properties.PropertyDef;
import org.owpk.storage.RootedStorage;
import org.owpk.storage.Storage;
import org.owpk.storage.impl.LocalFileStorage;

public class ApiKeyProps extends AbsPropertiesProvider {
    public static final PropertyDef API_KEY = new PropertyDef("api_key", "");

    public static record ApiKey(String token) {
    }

    public ApiKeyProps(Storage storage) {
        super("creds.properties", storage);
    }

    public ApiKeyProps() {
        this(new RootedStorage(AppBaseProps.DEF_GIGACHAT_CLI_HOME.value(), new LocalFileStorage()));
    }

    @Override
    public void bootstrapValidation() {
    }

    public ApiKey apiKey() {
        var accessToken = getProperty(API_KEY);
        return new ApiKey(accessToken);
    }

    @Override
    protected List<PropertyDef> getDefaultProperties() {
        return List.of(API_KEY);
    }

    public synchronized void rewrite(ApiKey token) {
        setProperty(API_KEY, token.token());
    }
}
