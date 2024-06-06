package org.e2immu.cstimpl.analysis;

import org.e2immu.cstapi.analysis.Value;

public class ValueImpl implements Value {
    public static final ValueImpl FALSE = new ValueImpl(0);
    public static final ValueImpl TRUE = new ValueImpl(1);

    private final int value;

    public ValueImpl(int value) {
        this.value = value;
    }

    @Override
    public boolean isFalse() {
        return value == FALSE.value;
    }


    @Override
    public boolean isTrue() {
        return value == TRUE.value;
    }
}
