package org.e2immu.cstimpl.expression;

import org.e2immu.annotation.NotNull;
import org.e2immu.cstapi.expression.*;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.runtime.Runtime;

import java.util.List;
import java.util.Objects;

public class NegationImpl extends UnaryOperatorImpl implements Negation {

    public NegationImpl(MethodInfo operator, Precedence precedence, Expression expression) {
        super(operator, expression, precedence);
    }

    @Override
    public Double numericValue() {
        Double d = expression.numericValue();
        return d == null ? null : -d;
    }

    @Override
    public boolean isNegatedOrNumericNegative() {
        return true;
    }

}
