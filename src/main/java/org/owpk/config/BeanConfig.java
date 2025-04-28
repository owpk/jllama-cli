package org.owpk.config;

import org.owpk.config.properties.core.PropertiesManager;
import org.owpk.llm.ollama.client.OllamaProps;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

@Factory
public class BeanConfig {

    @Bean
    public PropertiesManager propertiesManager() {
        return new PropertiesManager();
    }

    @Bean
    public DynamicHttpClientUrlFilter dynamicBaseUrlFilter(PropertiesManager propertiesManager) {
        return new DynamicHttpClientUrlFilter((OllamaProps) propertiesManager.getProvider(OllamaProps.class));
    }
}
