package org.owpk.config.properties.validation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationResult {
	private final List<PropertyValidationResult> validationResults;
	private final boolean isValid;
}
