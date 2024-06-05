package org.e2immu.cstimpl.expression.eval;

import org.e2immu.cstapi.expression.*;
import org.e2immu.cstapi.runtime.Runtime;

public class EvalConstant {
    private final Runtime runtime;

    public EvalConstant(Runtime runtime) {
        this.runtime = runtime;
    }

    public Expression equalsExpression(ConstantExpression<?> l, ConstantExpression<?> r) {
        if (l instanceof NullConstant || r instanceof NullConstant)
            throw new UnsupportedOperationException("Not for me");

        if (l instanceof StringConstant ls && r instanceof StringConstant rs) {
            return runtime.newBooleanConstant(ls.constant().equals(rs.constant()));
        }
        if (l instanceof BooleanConstant lb && r instanceof BooleanConstant lr) {
            return runtime.newBooleanConstant(lb.constant() == lr.constant());
        }
        if (l instanceof CharConstant lc && r instanceof CharConstant rc) {
            return runtime.newBooleanConstant(lc.constant() == rc.constant());
        }
        if (l instanceof Numeric ln && r instanceof Numeric rn) {
            return runtime.newBooleanConstant(ln.number().equals(rn.number()));
        }
        throw new UnsupportedOperationException("l = " + l + ", r = " + r);
    }
}
