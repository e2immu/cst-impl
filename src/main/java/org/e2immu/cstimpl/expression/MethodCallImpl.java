package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.MethodCall;
import org.e2immu.cstapi.expression.Precedence;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MethodCallImpl extends  ExpressionImpl implements MethodCall {
    public MethodCallImpl(int complexity) {
        super(complexity);
    }

    @Override
    public MethodInfo methodInfo() {
        return null;
    }

    @Override
    public Expression object() {
        return null;
    }

    @Override
    public List<Expression> parameterExpressions() {
        return List.of();
    }

    @Override
    public String modificationTimes() {
        return "";
    }

    @Override
    public boolean objectIsImplicit() {
        return false;
    }

    @Override
    public ParameterizedType concreteReturnType() {
        return null;
    }

    @Override
    public ParameterizedType parameterizedType() {
        return null;
    }

    @Override
    public Precedence precedence() {
        return null;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public int internalCompareTo(Expression expression) {
        return 0;
    }

    @Override
    public void visit(Predicate<Element> predicate) {

    }

    @Override
    public void visit(Visitor visitor) {

    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        return null;
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
