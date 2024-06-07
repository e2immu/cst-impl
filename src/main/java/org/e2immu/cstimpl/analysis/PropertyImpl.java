package org.e2immu.cstimpl.analysis;

import org.e2immu.cstapi.analysis.Property;

public class PropertyImpl implements Property {
    public static final Property MODIFIED_METHOD = new PropertyImpl("modifiedMethod");
    public static final Property FLUENT = new PropertyImpl("fluent");
    public static final Property FIELD_FINAL = new PropertyImpl("final");
    public static final Property MODIFIED_PARAMETER = new PropertyImpl("modifiedParameter");
    public static final Property FIELD_NOT_NULL = new PropertyImpl("fieldNotNull");
    public static final Property METHOD_NOT_NULL = new PropertyImpl("methodNotNull");

    private final String key;

    public PropertyImpl(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
