package org.e2immu.cstimpl.info;


import org.e2immu.annotation.Fluent;

public class ParameterInspectionImpl extends InspectionImpl implements ParameterInspection {

    private final boolean varArgs;

    public ParameterInspectionImpl(Inspection inspection, boolean varArgs) {
        super(inspection.access(), inspection.comments(), inspection.source(), inspection.isSynthetic(),
                inspection.annotations());
        this.varArgs = varArgs;
    }

    @Override
    public boolean isVarArgs() {
        return varArgs;
    }

    public static class Builder extends InspectionImpl.Builder implements ParameterInspection {
        private boolean varArgs;

        @Override
        public boolean isVarArgs() {
            return varArgs;
        }

        @Fluent
        public Builder setVarArgs(boolean varArgs) {
            this.varArgs = varArgs;
            return this;
        }

        @Override
        public ParameterInspectionImpl build() {
            return new ParameterInspectionImpl(this, varArgs);
        }
    }
}
