package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.BooleanConstant;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Negatable;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstimpl.expression.util.ExpressionComparator;

public class BooleanConstantImpl extends ConstantExpressionImpl<Boolean> implements Negatable {
    private final ParameterizedType booleanPt;
    private final boolean constant;

    public BooleanConstantImpl(ParameterizedType booleanPt, boolean constant) {
        this.booleanPt = booleanPt;
        this.constant = constant;
    }

    @Override
    public Boolean constant() {
        return constant;
    }

    @Override
    public ParameterizedType parameterizedType() {
        return booleanPt;
    }

    @Override
    public int order() {
        return ExpressionComparator.ORDER_CONSTANT_BOOLEAN;
    }

    @Override
    public int internalCompareTo(Expression expression) {
        BooleanConstant bc = (BooleanConstant) expression;
        if (constant == bc.constant()) return 0;
        return constant ? -1 : 1;
    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        return null;
    }

    @Override
    public Expression negate() {
        return new BooleanConstantImpl(booleanPt, !constant);
    }
}
