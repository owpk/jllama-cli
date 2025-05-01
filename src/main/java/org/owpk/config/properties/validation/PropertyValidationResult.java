package org.owpk.config.properties.validation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropertyValidationResult {
	private final String propertyKey;
	private final boolean isValid;
	private final String message;
}
