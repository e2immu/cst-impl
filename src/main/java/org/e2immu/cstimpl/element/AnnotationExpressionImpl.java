package org.e2immu.cstimpl.element;

import org.e2immu.cstapi.expression.AnnotationExpression;
import org.e2immu.cstapi.info.TypeInfo;

import java.util.List;

public class AnnotationExpressionImpl implements AnnotationExpression {
    private final TypeInfo typeInfo;

    private final List<KV> keyValuePairs;

    public AnnotationExpressionImpl(TypeInfo typeInfo, List<KV> keyValuePairs) {
        this.typeInfo = typeInfo;
        this.keyValuePairs = keyValuePairs;
    }

    @Override
    public TypeInfo typeInfo() {
        return typeInfo;
    }

    @Override
    public List<KV> keyValuePairs() {
        return keyValuePairs;
    }
}
