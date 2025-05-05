package org.owpk;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.owpk.config.properties.PropertiesManager;
import org.owpk.config.properties.model.ApplicationProperties;
import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.config.properties.model.RoleProps;
import org.owpk.service.dialog.YamlDialogRepositoryImpl;
import org.owpk.service.role.def.DefaultRoles;
import org.owpk.storage.Storage;
import org.owpk.utils.serializers.ComplexSerializer;

import reactor.core.publisher.Mono;

class YamlDialogRepositoryImplTest {

	@Mock
	private Storage storage;

	@Mock
	private PropertiesManager propertiesManager;

	@Mock
	private ComplexSerializer serializer;

	@InjectMocks
	private YamlDialogRepositoryImpl dialogRepository;

	private static final String EXT = ".yaml";
	private static final String DIALOG_ID = "2023-10-05-140000-000";
	private static final String DIALOG_FILE = DIALOG_ID + EXT;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveMessages() throws Exception {
		when(propertiesManager.getApplicationProperties()).thenReturn(mockedProperties());
		when(storage.createFileOrDirIfNotExists(eq(false), anyString(), anyString())).thenReturn("path/to/dialog.yaml");
		when(serializer.dump(anyList())).thenReturn(new byte[0]);

		Mono<Void> result = dialogRepository.saveMessages(DIALOG_ID, Collections.emptyList());
		assertNull(result.block());

		verify(storage, times(1)).createFileOrDirIfNotExists(eq(false), anyString(), eq("2023-10-05-140000-000.yaml"));
	}

	private ApplicationProperties mockedProperties() {
		var llmProvider = new LlmProviderProperties("ollama", "http://mock.ollama:11434");
		llmProvider.setModel("mock");
		var properties = new ApplicationProperties();
		properties.setChatsPath("/path/to/chat.yaml");
		properties.setCurrentChatId(DIALOG_ID);
		properties.setDefaulProvider("ollama");
		properties.setDefaultRoleName("default");
		properties.setLlmProviders(new LlmProviderProperties[] { llmProvider });
		properties.setRoles(DefaultRoles.getDefaultRoles().stream().map(it -> {
			var props = new RoleProps();
			props.setName(it.getId());
			props.setPrompt(it.getPrompt());
			return props;
		}).toArray(RoleProps[]::new));
		return properties;
	}
}