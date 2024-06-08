package org.e2immu.cstimpl.info;


import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.info.FieldInfo;
import org.e2immu.cstapi.info.FieldModifier;

import java.util.HashSet;
import java.util.Set;


public class FieldInspectionImpl extends InspectionImpl implements FieldInspection {
    private final Set<FieldModifier> fieldModifiers;
    private final Expression initializer;

    public FieldInspectionImpl(Inspection inspection, Set<FieldModifier> fieldModifiers, Expression initializer) {
        super(inspection.access(), inspection.comments(), inspection.source(), inspection.isSynthetic(),
                inspection.annotations());
        this.fieldModifiers = fieldModifiers;
        this.initializer = initializer;
    }

    @Override
    public Expression initializer() {
        return initializer;
    }


    @Override
    public Set<FieldModifier> fieldModifiers() {
        return fieldModifiers;
    }

    @SuppressWarnings("unchecked")
    public static class Builder<B extends Builder<?>> extends InspectionImpl.Builder<B> implements FieldInfo.Builder<B>, FieldInspection {
        private final FieldInfoImpl fieldInfo;
        private final Set<FieldModifier> fieldModifiers = new HashSet<>();
        private Expression initializer;

        public Builder(FieldInfoImpl fieldInfo) {
            this.fieldInfo = fieldInfo;
        }

        @Override
        public B addFieldModifier(FieldModifier fieldModifier) {
            fieldModifiers.add(fieldModifier);
            return (B) this;
        }

        @Override
        public B setInitializer(Expression initializer) {
            this.initializer = initializer;
            return (B) this;
        }

        @Override
        public void commit() {
            fieldInfo.commit(new FieldInspectionImpl(this, Set.copyOf(fieldModifiers), initializer));
        }

        @Override
        public Expression initializer() {
            return initializer;
        }

        @Override
        public Set<FieldModifier> fieldModifiers() {
            return fieldModifiers;
        }
    }
}
