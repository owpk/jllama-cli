package org.owpk.config.properties.validator;

import java.util.Map;

public interface PropertiesValidator {

    /**
     * Validates the given properties.
     *
     * @param properties the properties to validate
     * @throws PropertyValidationException if the properties are invalid
     */
    void validate(Map<String, String> properties) throws PropertyValidationException;

    /**
     * Checks if the given properties are valid.
     *
     * @param properties the properties to check
     * @return true if the properties are valid, false otherwise
     */
    boolean isValid(Map<String, String> properties);
}