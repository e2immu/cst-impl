package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.expression.Expression;

import java.util.List;

public abstract class ExpressionImpl implements Expression {

    public static final int HARD_LIMIT_ON_COMPLEXITY = 5000;
    public static final int SOFT_LIMIT_ON_COMPLEXITY = 500;
    public static final int CONSTRUCTOR_CALL_EXPANSION_LIMIT = 20;
    public static final int COMPLEXITY_LIMIT_OF_INLINED_METHOD = 1000;

    @Override
    public Source source() {
        return null;
    }

    @Override
    public List<Comment> comments() {
        return List.of();
    }
}
