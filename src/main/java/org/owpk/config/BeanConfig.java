package org.owpk.config;

import java.util.Optional;

import org.owpk.config.properties.PropertiesManager;
import org.owpk.service.LlmServiceFactory;
import org.owpk.service.dialog.YamlDialogRepositoryImpl;
import org.owpk.service.role.RolesManager;
import org.owpk.utils.serializers.ComplexSerializer;
import org.owpk.utils.serializers.YamlObjectSerializer;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.http.client.StreamingHttpClient;
import io.micronaut.http.client.annotation.Client;
import lombok.extern.slf4j.Slf4j;

@Factory
@Slf4j
public class BeanConfig {

	@Bean
	public ComplexSerializer yamlObjectMapper() {
		return new YamlObjectSerializer();
	}

	@Bean
	public RolesManager rolesManager(PropertiesManager manager) {
		return new RolesManager(yamlObjectMapper(), manager.getApplicationProperties());
	}

	@Bean
	public YamlDialogRepositoryImpl dialogRepository(ComplexSerializer complexSerializer,
			PropertiesManager propertiesManager) {
		var applicationProperties = propertiesManager.getApplicationProperties();
		var chatsPath = Optional.ofNullable(applicationProperties.getChatsPath()).orElse("");

		var storage = propertiesManager.getStorage(); // TODO default is home directory storage, maybe change later
														// according to configuration e.g. chat storage type
		if (chatsPath.isBlank() || !storage.exists(chatsPath)) {
			log.info("Creating default chats directory {}, because it does not exist", chatsPath);
			chatsPath = storage.createFileOrDirIfNotExists(true, propertiesManager.getAppHomeDir(),
					ApplicationConstants.APP_CHATS_DIR_NAME);
			applicationProperties.setChatsPath(chatsPath);
			propertiesManager.getApplicationPropertiesProvider().onPropertiesChanged(applicationProperties);
		}

		return new YamlDialogRepositoryImpl(storage, propertiesManager, complexSerializer);
	}

	@Bean
	public LlmServiceFactory llmServiceFactory(@Client StreamingHttpClient httpClient,
			PropertiesManager propertiesManager,
			YamlDialogRepositoryImpl dialogRepository, RolesManager rolesManager) {
		return new LlmServiceFactory(httpClient, propertiesManager, dialogRepository, rolesManager);
	}
}
