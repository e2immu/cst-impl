package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class VariableExpressionImpl extends ExpressionImpl {

    private final Variable variable;

    public VariableExpressionImpl(Variable variable) {
        this.variable = variable;
    }

    @Override
    public ParameterizedType parameterizedType() {
        return variable.parameterizedType();
    }

    @Override
    public int compareTo(Expression o) {
        return 0;
    }

    @Override
    public int complexity() {
        return variable.complexity();
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
    public Stream<TypeReference> typesReferenced() {
        return variable.typesReferenced();
    }
}
