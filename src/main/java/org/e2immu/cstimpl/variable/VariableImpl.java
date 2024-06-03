package org.e2immu.cstimpl.variable;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.Variable;

import java.util.List;

public abstract class VariableImpl implements Variable {

    private final ParameterizedType parameterizedType;

    public VariableImpl(ParameterizedType parameterizedType) {
        this.parameterizedType = parameterizedType;
    }

    @Override
    public ParameterizedType parameterizedType() {
        return parameterizedType;
    }

    @Override
    public List<Comment> comments() {
        return List.of();
    }

    @Override
    public Source source() {
        return null;
    }
}
