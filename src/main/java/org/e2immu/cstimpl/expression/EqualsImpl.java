package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Product;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.runtime.Runtime;
import org.e2immu.cstimpl.expression.util.PrecedenceEnum;

public class EqualsImpl extends BinaryOperatorImpl implements Product {

    public EqualsImpl(Runtime runtime, MethodInfo equalityOperator, Expression lhs, Expression rhs) {
        super(equalityOperator, runtime.precedenceEQUALITY(), lhs, rhs);
    }

}
