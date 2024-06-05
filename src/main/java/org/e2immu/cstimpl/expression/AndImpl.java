package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.*;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.runtime.Runtime;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.expression.util.*;
import org.e2immu.cstimpl.output.OutputBuilderImpl;
import org.e2immu.cstimpl.output.Symbol;
import org.e2immu.cstimpl.util.ListUtil;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class AndImpl extends ExpressionImpl implements And {

    private final List<Expression> expressions;
    private final ParameterizedType booleanPt;

    public AndImpl(Runtime runtime, List<Expression> expressions) {
        super(1 + expressions.stream().mapToInt(Expression::complexity).sum());
        this.expressions = expressions;
        booleanPt = runtime.booleanParameterizedType();
    }

    @Override
    public List<Expression> expressions() {
        return expressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        And andValue = (And) o;
        return expressions.equals(andValue.expressions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(expressions);
    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        Precedence precedence = precedence();
        return new OutputBuilderImpl()
                .add(expressions.stream().map(e -> outputInParenthesis(qualification, precedence, e))
                        .collect(OutputBuilderImpl.joining(Symbol.LOGICAL_AND)));
    }

    @Override
    public Stream<TypeReference> typesReferenced() {
        return expressions.stream().flatMap(Expression::typesReferenced);
    }

    @Override
    public ParameterizedType parameterizedType() {
        return booleanPt;
    }

    @Override
    public Precedence precedence() {
        return PrecedenceEnum.LOGICAL_AND;
    }

    @Override
    public int order() {
        return ExpressionComparator.ORDER_AND;
    }

    @Override
    public int internalCompareTo(Expression expression) {
        And andValue = (And) expression;
        return ListUtil.compare(expressions, andValue.expressions());
    }


    @Override
    public Stream<Variable> variables(DescendMode descendIntoFieldReferences) {
        return expressions.stream().flatMap(v -> v.variables(descendIntoFieldReferences));
    }

    @Override
    public void visit(Predicate<Element> predicate) {
        if (predicate.test(this)) {
            expressions.forEach(v -> v.visit(predicate));
        }
    }

    @Override
    public void visit(Visitor visitor) {
        if (visitor.beforeExpression(this)) {
            expressions.forEach(e -> e.visit(visitor));
        }
        visitor.afterExpression(this);
    }
}
