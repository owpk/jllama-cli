package org.owpk.config.properties.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationProperties {
	private LlmProviderProperties[] llmProviders;
	private RoleProps[] roles;
	private String defaulProvider;
	private String defaultRole;
	public String chatsPath;
}