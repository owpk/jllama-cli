package org.owpk.config.properties.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@ReflectiveAccess
public class ApplicationProperties {
	private LlmProviderProperties[] llmProviders;
	private RoleProps[] roles;
	private String defaulProvider;
	private String defaultRoleName;
	private String chatsPath;
	private String currentChatId;
}