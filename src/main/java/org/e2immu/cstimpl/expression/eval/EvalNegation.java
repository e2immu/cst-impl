package org.e2immu.cstimpl.expression.eval;

import org.e2immu.annotation.NotNull;
import org.e2immu.cstapi.expression.*;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.runtime.Runtime;
import org.e2immu.cstimpl.expression.NegationImpl;

import java.util.List;
import java.util.Objects;

public class EvalNegation {
    private final Runtime runtime;

    public EvalNegation(Runtime runtime) {
        this.runtime = runtime;
    }

    public Expression eval( boolean allowEqualsToCallContext, @NotNull Expression v) {
        Objects.requireNonNull(v);
        if (v instanceof BooleanConstant boolValue) {
            return boolValue.negate();
        }
        if (v instanceof Negatable negatable) {
            return negatable.negate();
        }
        if (v.isEmpty()) return v;

        if (v instanceof Or or) {
            List<Expression> negated = or.expressions().stream().map(runtime::negate).toList();
            return runtime.newAnd(negated);
        }
        if (v instanceof And and) {
            List<Expression> negated = and.expressions().stream().map(runtime::negate).toList();
            return runtime.newOr(negated);
        }
        if (v instanceof Sum sum) {
            return runtime.sum(runtime.negate(sum.lhs()), runtime.negate(sum.rhs()));
        }
        if (v instanceof GreaterThanZero greaterThanZero) {
            return greaterThanZero.negate(context);
        }

        if (v instanceof Equals equals) {
            InlineConditional icl;
            if ((icl = equals.lhs().asInstanceOf(InlineConditional.class)) != null) {
                EvaluationResult safeEvaluationContext = context.copyToPreventAbsoluteStateComputation();
                Expression result = Equals.tryToRewriteConstantEqualsInlineNegative(safeEvaluationContext,
                        allowEqualsToCallContext, equals.rhs, icl);
                if (result != null) return result;
            }
            InlineConditional icr;
            if ((icr = equals.rhs().asInstanceOf(InlineConditional.class)) != null) {
                EvaluationResult safeEvaluationContext = context.copyToPreventAbsoluteStateComputation();
                Expression result = Equals.tryToRewriteConstantEqualsInlineNegative(safeEvaluationContext,
                        allowEqualsToCallContext, equals.lhs(), icr);
                if (result != null) return result;
            }
        }

        MethodInfo operator = v.isNumeric() ? runtime.unaryMinusOperatorInt() : runtime.logicalNotOperatorBool();
        Negation negation = new NegationImpl(operator, runtime.precedenceUNARY(), v);

        if (v instanceof InstanceOf i) {
            Expression varIsNull = runtime.equals(allowEqualsToCallContext, runtime.nullConstant(), i.expression());
            return runtime.newOr(allowEqualsToCallContext, negation, varIsNull);
        }
        return negation;
    }
}
