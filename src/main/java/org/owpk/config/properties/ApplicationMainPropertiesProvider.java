package org.owpk.config.properties;

import java.util.List;

import org.owpk.config.LlmSupports;
import org.owpk.config.properties.model.ApplicationProperties;
import org.owpk.config.properties.model.LlmProviderProperties;
import org.owpk.config.properties.model.RoleProps;
import org.owpk.config.properties.provider.ObjectPropsProvider;
import org.owpk.config.properties.provider.PropertiesProccessingException;
import org.owpk.service.role.Role;
import org.owpk.service.role.def.DefaultRoles;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationMainPropertiesProvider implements ObjectPropsProvider<ApplicationProperties> {
	private final ObjectPropsProvider<ApplicationProperties> delegate;

	public ApplicationMainPropertiesProvider(ObjectPropsProvider<ApplicationProperties> delegate) {
		this.delegate = delegate;
	}

	public ApplicationProperties createDefaults() {
		log.info("Creating default properties: " + this.getClass().getSimpleName());
		return new ApplicationProperties(
				getSupportedLlmProviders().toArray(LlmProviderProperties[]::new),
				DefaultRoles.getDefaultRoles().stream().map(this::toRoleProps).toArray(RoleProps[]::new),
				LlmSupports.KnownLlm.OLLAMA.getName(),
				DefaultRoles.getDEFAULT().getId(), "", "");
	}

	@Override
	public void save(ApplicationProperties value) throws PropertiesProccessingException {
		delegate.save(value);
	}

	@Override
	public ApplicationProperties load() throws PropertiesProccessingException {
		return delegate.load();
	}

	@Override
	public void onPropertiesChanged(ApplicationProperties value) {
		delegate.onPropertiesChanged(value);
	}

	private List<LlmProviderProperties> getSupportedLlmProviders() {
		return LlmSupports.SUPPORTED_PROVIDERS.stream()
				.map(it -> new LlmProviderProperties(it.getName(), it.getDefaultUrl()))
				.toList();
	}

	private RoleProps toRoleProps(Role role) {
		return new RoleProps(role.getId(), role.getPrompt());
	}

}
