package org.e2immu.cstimpl.analysis;

import org.e2immu.cstapi.analysis.Property;
import org.e2immu.cstapi.analysis.Value;

public class PropertyImpl implements Property {
    // type
    public static final Property IMMUTABLE_TYPE = new PropertyImpl("immutableType", Value.Immutable.class);

    // method
    public static final Property MODIFIED_METHOD = new PropertyImpl("modifiedMethod");
    public static final Property FLUENT_METHOD = new PropertyImpl("fluentMethod");
    public static final Property IDENTITY_METHOD = new PropertyImpl("identityMethod");
    public static final Property NOT_NULL_METHOD = new PropertyImpl("notNullMethod");
    public static final Property STATIC_SIDE_EFFECTS_METHOD = new PropertyImpl("sseMethod");

    // commutation on methods
    public static final Property PARALLEL_PARAMETER_GROUPS = new PropertyImpl("parallelParameterGroups",
            Value.ParameterParSeq.class);
    public static final Property COMMUTABLE_METHODS = new PropertyImpl("commutableMethods",
            Value.CommutableData.class);
    public static final Property GET_SET_FIELD = new PropertyImpl("getSetField", Value.FieldValue.class);
    public static final Property GET_SET_EQUIVALENT = new PropertyImpl("getSetEquivalent",
            Value.GetSetEquivalent.class);

    // parameter
    public static final Property MODIFIED_PARAMETER = new PropertyImpl("modifiedParameter");
    public static final Property IGNORE_MODIFICATIONS_PARAMETER = new PropertyImpl("ignoreModsParameter");

    // field
    public static final Property FINAL_FIELD = new PropertyImpl("finalField");
    public static final Property NOT_NULL_FIELD = new PropertyImpl("notNullField");

    // statement
    public static final Property ALWAYS_ESCAPES = new PropertyImpl("statementAlwaysEscapes");


    private final String key;
    private final Class<? extends Value> classOfValue;

    public PropertyImpl(String key) {
        this(key, Value.Bool.class);
    }

    public PropertyImpl(String key, Class<? extends Value> classOfValue) {
        this.key = key;
        this.classOfValue = classOfValue;
    }

    public String getKey() {
        return key;
    }

    @Override
    public Class<? extends Value> classOfValue() {
        return classOfValue;
    }
}
