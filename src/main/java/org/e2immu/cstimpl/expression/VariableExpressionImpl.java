package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Precedence;
import org.e2immu.cstapi.expression.VariableExpression;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.element.ElementImpl;
import org.e2immu.cstimpl.expression.util.ExpressionComparator;
import org.e2immu.cstimpl.expression.util.InternalCompareToException;
import org.e2immu.cstimpl.expression.util.PrecedenceEnum;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class VariableExpressionImpl extends ExpressionImpl implements VariableExpression {

    private final Variable variable;
    private final Suffix suffix;

    public VariableExpressionImpl(Variable variable) {
        this(null, List.of(), variable, null);
    }

    public VariableExpressionImpl(Source source, List<Comment> comments, Variable variable, Suffix suffix) {
        super(comments, source, variable.complexity());
        this.variable = variable;
        this.suffix = suffix;
    }

    @Override
    public Suffix suffix() {
        return suffix;
    }

    @Override
    public Variable variable() {
        return variable;
    }

    public static class Builder extends ElementImpl.Builder<VariableExpression.Builder> implements VariableExpression.Builder {
        private Variable variable;
        private Suffix suffix;

        @Override
        public VariableExpression.Builder setVariable(Variable variable) {
            this.variable = variable;
            return this;
        }

        @Override
        public VariableExpression.Builder setSuffix(Suffix suffix) {
            this.suffix = suffix;
            return this;
        }

        @Override
        public VariableExpression build() {
            return new VariableExpressionImpl(source, comments, variable, suffix);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableExpressionImpl that = (VariableExpressionImpl) o;
        return Objects.equals(variable, that.variable) && Objects.equals(suffix, that.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable, suffix);
    }

    @Override
    public VariableExpression withSuffix(Suffix suffix) {
        return new VariableExpressionImpl(source(), comments(), variable, suffix);
    }

    @Override
    public ParameterizedType parameterizedType() {
        return variable.parameterizedType();
    }

    @Override
    public void visit(Predicate<Element> predicate) {
        if (predicate.test(this)) {
            variable.visit(predicate);
        }
    }

    @Override
    public void visit(Visitor visitor) {
        if (visitor.beforeExpression(this)) {
            variable.visit(visitor);
        }
        visitor.afterExpression(this);
    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        return variable.print(qualification);
    }

    @Override
    public Stream<Variable> variables(DescendMode descendMode) {
        return variable.variables(descendMode);
    }

    @Override
    public Stream<Element.TypeReference> typesReferenced() {
        return variable.typesReferenced();
    }

    @Override
    public Precedence precedence() {
        return PrecedenceEnum.TOP;
    }

    @Override
    public int order() {
        return ExpressionComparator.ORDER_VARIABLE;
    }

    @Override
    public int internalCompareTo(Expression expression) {
        VariableExpression ve;
        if ((ve = expression.asInstanceOf(VariableExpression.class)) != null) {
            return variable.fullyQualifiedName().compareTo(ve.variable().fullyQualifiedName());
        }
        throw new InternalCompareToException();
    }
}
