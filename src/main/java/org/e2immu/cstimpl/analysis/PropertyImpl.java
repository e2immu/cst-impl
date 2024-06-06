package org.e2immu.cstimpl.analysis;

import org.e2immu.cstapi.analysis.Property;

public class PropertyImpl implements Property {
    public static final Property MODIFIED_METHOD = new PropertyImpl("modifiedMethod");
    public static final Property FLUENT = new PropertyImpl("fluent");

    private final String key;

    public PropertyImpl(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
