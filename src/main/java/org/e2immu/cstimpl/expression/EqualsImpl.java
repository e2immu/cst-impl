package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.Equals;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Precedence;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.runtime.Runtime;

public class EqualsImpl extends BinaryOperatorImpl implements Equals {
    public EqualsImpl(Runtime runtime, MethodInfo equalityOperator, Expression lhs, Expression rhs) {
        super(equalityOperator, runtime.precedenceEQUALITY(), lhs, rhs);
    }

    public EqualsImpl(MethodInfo equalityOperator, Precedence precedence, Expression lhs, Expression rhs) {
        super(equalityOperator, precedence, lhs, rhs);
    }

}
