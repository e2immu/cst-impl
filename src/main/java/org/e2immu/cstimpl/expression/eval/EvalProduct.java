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
    public Expression eval(Expression lhs, Expression rhs) {
        Expression l = runtime.sortAndSimplify(lhs);
        Expression r = runtime.sortAndSimplify(rhs);
        Double ln = l.numericValue();
        Double rn = r.numericValue();

        if (ln != null && ln == 0 || rn != null && rn == 0) {
            return runtime.zero();
        }

        if (ln != null && ln == 1) return r;
        if (rn != null && rn == 1) return l;
        if (ln != null && rn != null) return runtime.intOrDouble(ln * rn);

        // any unknown lingering
        if (l.isEmpty() || r.isEmpty()) throw new UnsupportedOperationException();

        if (r instanceof Sum sum) {
            return runtime.sum(runtime.product(l, sum.lhs()), runtime.product(l, sum.rhs()));
        }
        if (l instanceof Sum sum) {
            return runtime.sum(runtime.product(sum.lhs(), r), runtime.product(sum.rhs(), r));
        }
        return l.compareTo(r) < 0 ? new ProductImpl(runtime, l, r) : new ProductImpl(runtime, r, l);
    }
}
