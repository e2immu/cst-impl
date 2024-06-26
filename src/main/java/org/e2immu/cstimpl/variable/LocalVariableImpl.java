package org.e2immu.cstimpl.variable;

import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.LocalVariable;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.output.OutputBuilderImpl;
import org.e2immu.cstimpl.output.QualifiedNameImpl;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LocalVariableImpl extends VariableImpl implements LocalVariable {
    private final Expression assignmentExpression;
    private final String name;

    public LocalVariableImpl(String name, ParameterizedType parameterizedType, Expression assignmentExpression) {
        super(parameterizedType);
        this.name = name;
        this.assignmentExpression = assignmentExpression;
    }

    @Override
    public LocalVariable withAssignmentExpression(Expression expression) {
        return new LocalVariableImpl(name, parameterizedType(), expression);
    }

    @Override
    public LocalVariable withName(String name) {
        return new LocalVariableImpl(name, parameterizedType(), assignmentExpression);
    }

    @Override
    public LocalVariable withType(ParameterizedType type) {
        return new LocalVariableImpl(name, type, assignmentExpression);
    }

    @Override
    public Expression assignmentExpression() {
        return assignmentExpression;
    }

    @Override
    public String simpleName() {
        return name;
    }

    @Override
    public boolean isLocal() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalVariableImpl that = (LocalVariableImpl) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int complexity() {
        return 2;
    }

    @Override
    public void visit(Predicate<Element> predicate) {
        predicate.test(this);
    }

    @Override
    public void visit(Visitor visitor) {
        if (visitor.beforeVariable(this) && assignmentExpression != null) {
            assignmentExpression.visit(visitor);
        }
        visitor.afterVariable(this);
    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        String name = qualification.isFullyQualifiedNames() ? fullyQualifiedName() : simpleName();
        return new OutputBuilderImpl().add(new QualifiedNameImpl(name, null, QualifiedNameImpl.Required.NEVER));
    }

    @Override
    public Stream<Variable> variables(DescendMode descendMode) {
        return Stream.of(this);
    }

    @Override
    public Stream<TypeReference> typesReferenced() {
        return parameterizedType().typesReferenced();
    }
}
