package org.e2immu.cstimpl.expression;

import org.e2immu.annotation.NotNull;
import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Precedence;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstimpl.element.ElementImpl;
import org.e2immu.cstimpl.expression.util.ExpressionComparator;
import org.e2immu.cstimpl.output.OutputBuilderImpl;
import org.e2immu.cstimpl.output.Symbol;

import java.util.List;

public abstract class ExpressionImpl extends ElementImpl implements Expression {

    public static final int HARD_LIMIT_ON_COMPLEXITY = 5000;
    public static final int SOFT_LIMIT_ON_COMPLEXITY = 500;
    public static final int CONSTRUCTOR_CALL_EXPANSION_LIMIT = 20;
    public static final int COMPLEXITY_LIMIT_OF_INLINED_METHOD = 1000;

    private final int complexity;

    protected ExpressionImpl(int complexity) {
        this.complexity = complexity;
    }

    @Override
    public int complexity() {
        return complexity;
    }

    @Override
    public Source source() {
        return null;
    }

    @Override
    public List<Comment> comments() {
        return List.of();
    }

    @NotNull
    protected OutputBuilder outputInParenthesis(Qualification qualification, Precedence precedence, Expression expression) {
        if (precedence.greaterThan(expression.precedence())) {
            return new OutputBuilderImpl().add(Symbol.LEFT_PARENTHESIS).add(expression.print(qualification)).add(Symbol.RIGHT_PARENTHESIS);
        }
        return expression.print(qualification);
    }

    @Override
    public int compareTo(Expression v) {
        if (this == v || equals(v)) return 0;
        return ExpressionComparator.SINGLETON.compare(this, v);
    }


}
