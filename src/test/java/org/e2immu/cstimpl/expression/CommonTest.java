package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.VariableExpression;
import org.e2immu.cstapi.runtime.Runtime;
import org.e2immu.cstapi.variable.LocalVariable;
import org.e2immu.cstimpl.runtime.RuntimeImpl;

public abstract class CommonTest {
    protected final Runtime r = new RuntimeImpl();
    protected final LocalVariable vi = r.newLocalVariable("i", r.intParameterizedType());
    protected final LocalVariable vj = r.newLocalVariable("j", r.intParameterizedType());
    protected final VariableExpression i = r.newVariableExpression(vi);
    protected final VariableExpression j = r.newVariableExpression(vj);
}
