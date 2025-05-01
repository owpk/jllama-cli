package org.owpk.service.role;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.owpk.config.properties.model.ApplicationProperties;
import org.owpk.utils.serializers.YamlObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RolesManager {
	private final YamlObjectMapper yamlObjectMapper;
	private final Map<String, Role> applicationProperties;

	public RolesManager(YamlObjectMapper yamlObjectMapper, ApplicationProperties properties) {
		this.yamlObjectMapper = yamlObjectMapper;
		this.applicationProperties = Arrays.stream(properties.getRoles())
				.map(it -> new Role(it.getName(), it.getPrompt()))
				.collect(Collectors.toMap(it -> it.getId(), Function.identity()));
	}

	public Role getByRoleId(String roleId) {
		return Optional.ofNullable(applicationProperties.get(roleId))
				.orElseThrow(() -> new RuntimeException());
	}

	public Role parseRole(String yaml) {
		if (yaml == null || yaml.isBlank()) {
			throw new IllegalArgumentException("YAML string cannot be null or empty");
		}
		try {
			return yamlObjectMapper.convert(yaml, Role.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}