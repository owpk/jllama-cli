package org.owpk.config;

import java.util.Optional;

import org.owpk.config.properties.AppPropertiesConstants;
import org.owpk.config.properties.PropertiesManager;
import org.owpk.service.LlmServiceFactory;
import org.owpk.service.dialog.YamlDialogRepositoryImpl;
import org.owpk.service.role.RolesManager;
import org.owpk.utils.serializers.YamlObjectMapper;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.http.client.StreamingHttpClient;
import io.micronaut.http.client.annotation.Client;
import lombok.extern.slf4j.Slf4j;

@Factory
@Slf4j
public class BeanConfig {

	@Bean
	public YamlObjectMapper yamlObjectMapper() {
		return new YamlObjectMapper();
	}

	@Bean
	public RolesManager rolesManager(YamlObjectMapper yamlObjectMapper, PropertiesManager manager) {
		return new RolesManager(yamlObjectMapper, manager.getApplicationProperties());
	}

	@Bean
	public YamlDialogRepositoryImpl dialogRepository(YamlObjectMapper yamlObjectMapper,
			PropertiesManager propertiesManager) {
		var applicationProperties = propertiesManager.getApplicationProperties();
		var chatsPath = Optional.ofNullable(applicationProperties.getChatsPath()).orElse("");

		var storage = propertiesManager.getStorage(); // TODO default is home directory storage, maybe change later
														// according to configuration e.g. chat storage type
		if (chatsPath.isBlank() || !storage.exists(chatsPath)) {
			log.info("Creating default chats directory {}, because it does not exist", chatsPath);
			chatsPath = storage.createFileOrDirIfNotExists(true, propertiesManager.getAppHomeDir(),
					AppPropertiesConstants.APP_CHATS_DIR_NAME);
			applicationProperties.setChatsPath(chatsPath);
		}

		return new YamlDialogRepositoryImpl(storage, applicationProperties, yamlObjectMapper);
	}

	@Bean
	public LlmServiceFactory llmServiceFactory(@Client StreamingHttpClient httpClient,
			PropertiesManager propertiesManager,
			YamlDialogRepositoryImpl dialogRepository, RolesManager rolesManager) {
		return new LlmServiceFactory(httpClient, propertiesManager, dialogRepository, rolesManager);
	}
}
