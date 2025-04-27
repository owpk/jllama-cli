package org.owpk.config.properties.validator;

import org.owpk.config.properties.PropertyDef;

public class PropertyValidationException extends RuntimeException {
    public PropertyValidationException(PropertyDef property, String message) {
        super("Property '" + property.key() + "': " + message);
    }
}
