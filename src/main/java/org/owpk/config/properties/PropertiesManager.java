package org.owpk.config.properties;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.owpk.config.LlmSupports;
import org.owpk.config.properties.model.ApplicationProperties;
import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.config.properties.provider.PropertiesProccessingException;
import org.owpk.config.properties.provider.RemoteObjectPropsProvider;
import org.owpk.storage.Storage;
import org.owpk.storage.impl.LocalFileStorage;
import org.owpk.utils.serializers.Serializer;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Context
@Slf4j
@Getter
public class PropertiesManager {

    private final String appHomeDir;
    private final Storage storage;
    private final ApplicationMainPropertiesProvider applicationPropertiesProvider;
    private final ApplicationProperties applicationProperties;

    public PropertiesManager(Serializer yamlObjectMapper) throws PropertiesProccessingException {
        this.storage = new LocalFileStorage();
        this.appHomeDir = resolveHomeDir(storage);

        var mainPropsFilePath = storage.createFileOrDirIfNotExists(false, appHomeDir,
                AppPropertiesConstants.APP_CONFIG_FILE_NAME);

        var mainPropsLocalDelegate = new RemoteObjectPropsProvider<>(mainPropsFilePath, storage,
                ApplicationProperties.class, yamlObjectMapper);
        this.applicationPropertiesProvider = new ApplicationMainPropertiesProvider(mainPropsLocalDelegate);
        ApplicationProperties mainProps = null;

        try {
            mainProps = applicationPropertiesProvider.load();
        } catch (PropertiesProccessingException e) {
            log.info("Creating default properties by reason: {}", e.getMessage());
            mainProps = applicationPropertiesProvider.createDefaults();
        }

        applicationPropertiesProvider.save(mainProps);
        this.applicationProperties = mainProps;
    }

    private String resolveHomeDir(Storage storage) {
        var userHome = System.getProperty("user.home");
        return storage.createFileOrDirIfNotExists(true, userHome, "." + AppPropertiesConstants.APP_NAME);
    }

    @PreDestroy
    public void destroy() throws PropertiesProccessingException {
        log.info("Destroying PropertiesManager");
    }

    public LlmProviderProperties getLlmProviderProperties(LlmSupports.KnownLlm llm) {
        return Arrays.stream(this.applicationProperties.getLlmProviders())
                .collect(Collectors.toMap(it -> LlmSupports.KnownLlm.of(it.getProvider()), Function.identity()))
                .get(llm);
    }

    public LlmProviderProperties getDefaultLlmProperties() {
        return Arrays.stream(this.applicationProperties.getLlmProviders())
                .collect(Collectors.toMap(it -> LlmSupports.KnownLlm.of(it.getProvider()), Function.identity()))
                .get(LlmSupports.KnownLlm.of(this.applicationProperties.getDefaulProvider()));
    }

}