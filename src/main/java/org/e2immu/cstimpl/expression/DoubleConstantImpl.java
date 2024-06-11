package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.DoubleConstant;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.IntConstant;
import org.e2immu.cstapi.expression.Numeric;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.runtime.Predefined;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstimpl.expression.util.ExpressionComparator;
import org.e2immu.cstimpl.output.OutputBuilderImpl;
import org.e2immu.cstimpl.output.TextEnum;

public class DoubleConstantImpl extends ConstantExpressionImpl<Double> implements Numeric, DoubleConstant {

    private final double value;
    private final ParameterizedType parameterizedType;

    public DoubleConstantImpl(Predefined predefined, double value) {
        this(predefined.doubleParameterizedType(), value);
    }

    protected DoubleConstantImpl(ParameterizedType parameterizedType, double value) {
        this.parameterizedType = parameterizedType;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleConstantImpl that = (DoubleConstantImpl) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        return new OutputBuilderImpl().add(new TextEnum(Double.toString(value)));
    }

    @Override
    public Number number() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public ParameterizedType parameterizedType() {
        return parameterizedType;
    }

    @Override
    public int order() {
        return ExpressionComparator.ORDER_CONSTANT_DOUBLE;
    }

    @Override
    public int internalCompareTo(Expression expression) {
        return (int) Math.signum(value - ((DoubleConstant) expression).constant());
    }

    @Override
    public Double constant() {
        return value;
    }

    @Override
    public Expression negate() {
        return new DoubleConstantImpl(parameterizedType, -value);
    }
}
