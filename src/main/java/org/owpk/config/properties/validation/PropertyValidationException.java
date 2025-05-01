package org.owpk.config.properties.validation;

import org.owpk.config.properties.model.PropertyDef;

public class PropertyValidationException extends RuntimeException {
    public PropertyValidationException(PropertyDef property, String message) {
        super("Property '" + property.key() + "': " + message);
    }
}
