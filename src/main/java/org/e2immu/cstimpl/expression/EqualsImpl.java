package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.Equals;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Precedence;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.runtime.Runtime;
import org.e2immu.cstapi.translate.TranslationMap;

public class EqualsImpl extends BinaryOperatorImpl implements Equals {
    public EqualsImpl(Runtime runtime, MethodInfo equalityOperator, Expression lhs, Expression rhs) {
        super(equalityOperator, runtime.precedenceEquality(), lhs, rhs);
    }

    public EqualsImpl(MethodInfo equalityOperator, Precedence precedence, Expression lhs, Expression rhs) {
        super(equalityOperator, precedence, lhs, rhs);
    }

    @Override
    public Expression translate(TranslationMap translationMap) {
        Expression translated = translationMap.translateExpression(this);
        if (translated != this) return translated;

        Expression tl = lhs.translate(translationMap);
        Expression tr = rhs.translate(translationMap);
        if (tl == lhs && tr == rhs) return this;
        return new EqualsImpl(operator, precedence, tl, tr);
    }
}
