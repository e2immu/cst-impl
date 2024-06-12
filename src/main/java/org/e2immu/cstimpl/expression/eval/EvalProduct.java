package org.e2immu.cstimpl.expression.eval;

import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.Sum;
import org.e2immu.cstapi.runtime.Runtime;
import org.e2immu.cstimpl.expression.ProductImpl;

public class EvalProduct {

    private final Runtime runtime;

    public EvalProduct(Runtime runtime) {
        this.runtime = runtime;
    }

    // we try to maintain a sum of products
    public Expression eval(Expression l, Expression r) {
        Double ln = l.numericValue();
        Double rn = r.numericValue();

        if (ln != null && ln == 0 || rn != null && rn == 0) {
            return runtime.intZero();
        }

        if (ln != null && ln == 1) return r;
        if (rn != null && rn == 1) return l;
        if (ln != null && rn != null) return runtime.intOrDouble(ln * rn);

        // any unknown lingering
        if (l.isEmpty() || r.isEmpty()) throw new UnsupportedOperationException();

        if (r instanceof Sum sum) {
            Expression p1 = runtime.product(l, sum.lhs());
            Expression p2 = runtime.product(l, sum.rhs());
            return runtime.sum(p1, p2);
        }
        if (l instanceof Sum sum) {
            Expression p1 = runtime.product(sum.lhs(), r);
            Expression p2 = runtime.product(sum.rhs(), r);
            return runtime.sum(p1, p2);
        }
        return l.compareTo(r) < 0 ? new ProductImpl(runtime, l, r) : new ProductImpl(runtime, r, l);
    }
}
