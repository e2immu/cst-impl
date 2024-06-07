package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Product;
import org.e2immu.cstapi.runtime.Runtime;

public class ProductImpl extends BinaryOperatorImpl implements Product {

    public ProductImpl(Runtime runtime, Expression lhs, Expression rhs) {
        super(runtime.multiplyOperatorInt(), runtime.precedenceMULTIPLICATIVE(), lhs, rhs);
    }

}
