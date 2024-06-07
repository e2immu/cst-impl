package org.e2immu.cstimpl.runtime;

import org.e2immu.cstapi.expression.*;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.runtime.*;
import org.e2immu.cstapi.runtime.Runtime;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.expression.ExpressionImpl;

import java.util.List;
import java.util.stream.Stream;

public class RuntimeImpl extends FactoryImpl implements Runtime {
    private final Eval eval = new EvalImpl(this);

    @Override
    public Configuration configuration() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression inlineConditional(Expression condition,
                                        Expression ifTrue,
                                        Expression ifFalse,
                                        Variable myself,
                                        boolean modifying) {
        return eval.inlineConditional(condition, ifTrue, ifFalse, myself, modifying);
    }

    @Override
    public Expression product(Expression lhs, Expression rhs) {
        return eval.product(lhs, rhs);
    }

    @Override
    public Expression sum(Expression lhs, Expression rhs) {
        return eval.sum(lhs, rhs);
    }

    @Override
    public Expression negate(Expression expression) {
        return eval.negate(expression);
    }

    @Override
    public Expression remainder(Expression lhs, Expression rhs) {
        return eval.remainder(lhs, rhs);
    }

    @Override
    public Expression equals(Expression lhs, Expression rhs) {
        return eval.equals(lhs, rhs);
    }

    @Override
    public Expression greater(Expression lhs, Expression rhs, boolean allowEquals) {
        return eval.greater(lhs, rhs, allowEquals);
    }

    @Override
    public Expression greaterThanZero(Expression expression) {
        return eval.greaterThanZero(expression);
    }

    @Override
    public Expression or(List<Expression> expressions) {
        return eval.or(expressions);
    }

    @Override
    public Expression or(Expression... expressions) {
        return eval.or(expressions);
    }

    @Override
    public Expression and(Expression... expressions) {
        return eval.and(expressions);
    }

    @Override
    public Expression and(List<Expression> expressions) {
        return eval.and(expressions);
    }

    @Override
    public Expression divide(Expression lhs, Expression rhs) {
        return eval.divide(lhs, rhs);
    }

    @Override
    public boolean isNotNull0(Expression expression) {
        return eval.isNotNull0(expression);
    }

    @Override
    public int limitOnComplexity() {
        return ExpressionImpl.SOFT_LIMIT_ON_COMPLEXITY;
    }

    @Override
    public Stream<Expression> expandTerms(Expression expression, boolean negate) {
        return eval.expandTerms(expression, negate);
    }

    @Override
    public TypeInfo getFullyQualified(String name, boolean complain) {
        throw new UnsupportedOperationException(); // FIXME
    }
}
