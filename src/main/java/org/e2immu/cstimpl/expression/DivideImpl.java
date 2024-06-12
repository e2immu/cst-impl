package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Product;
import org.e2immu.cstapi.runtime.Runtime;

public class DivideImpl extends BinaryOperatorImpl implements Product {

    public DivideImpl(Runtime runtime, Expression lhs, Expression rhs) {
        super(runtime.divideOperatorInt(), runtime.precedenceMultiplicative(), lhs, rhs);
    }

}
