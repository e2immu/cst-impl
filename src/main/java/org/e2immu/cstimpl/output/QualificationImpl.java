package org.e2immu.cstimpl.output;

import org.e2immu.cstapi.output.Qualification;

public enum QualificationImpl implements Qualification {
    FULLY_QUALIFIED_NAMES,
    SIMPLE_NAMES;

    @Override
    public boolean isFullyQualifiedNames() {
        return this == FULLY_QUALIFIED_NAMES;
    }
}
