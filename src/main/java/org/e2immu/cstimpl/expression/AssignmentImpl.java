package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.Assignment;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Precedence;
import org.e2immu.cstapi.expression.VariableExpression;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.expression.util.ExpressionComparator;
import org.e2immu.cstimpl.expression.util.InternalCompareToException;
import org.e2immu.cstimpl.expression.util.PrecedenceEnum;
import org.e2immu.cstimpl.output.OutputBuilderImpl;
import org.e2immu.cstimpl.output.SymbolEnum;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class AssignmentImpl extends ExpressionImpl implements Assignment {
    private final Expression target;
    private final Expression value;
    private final Variable variableTarget;
    private final MethodInfo assignmentOperator;
    private final boolean assignmentOperatorIsPlus;
    private final MethodInfo binaryOperator;
    private final Boolean prefixPrimitiveOperator;

    public AssignmentImpl(Expression target, Expression value) {
        this(target, value, null, false, null, null);
    }

    public AssignmentImpl(Expression target, Expression value, MethodInfo assignmentOperator,
                          boolean assignmentOperatorIsPlus, MethodInfo binaryOperator, Boolean prefixPrimitiveOperator) {
        super(1 + target.complexity() + value.complexity());
        this.target = target;
        this.value = value;
        this.variableTarget = target instanceof VariableExpression ve ? ve.variable() : null;
        this.assignmentOperator = assignmentOperator;
        this.assignmentOperatorIsPlus = assignmentOperatorIsPlus;
        this.binaryOperator = binaryOperator;
        this.prefixPrimitiveOperator = prefixPrimitiveOperator;
    }

    @Override
    public Expression target() {
        return target;
    }

    @Override
    public Expression value() {
        return value;
    }

    @Override
    public Variable variableTarget() {
        return variableTarget;
    }

    @Override
    public MethodInfo assignmentOperator() {
        return null;
    }

    @Override
    public Boolean prefixPrimitiveOperator() {
        return null;
    }

    @Override
    public MethodInfo binaryOperator() {
        return null;
    }

    @Override
    public ParameterizedType parameterizedType() {
        return target.parameterizedType();
    }

    @Override
    public Precedence precedence() {
        return PrecedenceEnum.ASSIGNMENT;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public int internalCompareTo(Expression expression) {
        if (expression instanceof Assignment other) {
            int c = target.compareTo(other.target());
            if (c != 0) return c;
            return value.compareTo(other.value());
        }
        throw new InternalCompareToException();
    }

    @Override
    public void visit(Predicate<Element> predicate) {
        if (predicate.test(this)) {
            value.visit(predicate);
            target.visit(predicate);
        }
    }

    @Override
    public void visit(Visitor visitor) {
        if (visitor.beforeExpression(this)) {
            value.visit(visitor);
            target.visit(visitor);
        }
        visitor.afterExpression(this);
    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        if (prefixPrimitiveOperator != null) {
            String operator = assignmentOperatorIsPlus ? "++" : "--";
            if (prefixPrimitiveOperator) {
                return new OutputBuilderImpl().add(SymbolEnum.plusPlusPrefix(operator))
                        .add(outputInParenthesis(qualification, precedence(), target));
            }
            return new OutputBuilderImpl().add(outputInParenthesis(qualification, precedence(), target))
                    .add(SymbolEnum.plusPlusSuffix(operator));
        }
        //  != null && primitiveOperator != primitives.assignOperatorInt ? "=" + primitiveOperator.name : "=";
        String operator = assignmentOperator == null ? "=" : assignmentOperator.name();
        return new OutputBuilderImpl().add(outputInParenthesis(qualification, precedence(), target))
                .add(SymbolEnum.assignment(operator))
                .add(outputInParenthesis(qualification, precedence(), value));
    }

    @Override
    public Stream<Variable> variables(DescendMode descendMode) {
        return Stream.empty();
    }

    @Override
    public Stream<Element.TypeReference> typesReferenced() {
        return Stream.empty();
    }
}
