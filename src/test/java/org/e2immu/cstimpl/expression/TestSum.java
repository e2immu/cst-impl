package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.Expression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSum extends CommonTest {


    @Test
    public void test1() {
        Expression s = r.sum(r.one(), i);
        assertEquals("1+i", s.toString());
        Expression s2 = r.sum(r.newIntConstant(2), s);
        assertEquals("3+i", s2.toString());
    }

    @Test
    public void test2() {
        Expression s = r.sum(r.one(), r.sum(i, r.newIntConstant(3)));
        assertEquals("4+i", s.toString());
        Expression s2 = r.sum(r.sum(r.newIntConstant(3), r.newIntConstant(2)), r.sum(s, r.newIntConstant(-9)));
        assertEquals("i", s2.toString());
        Expression s3 = r.sum(r.sum(s2, r.newIntConstant(3)), r.negate(s2));
        assertEquals("3", s3.toString());
    }

    @Test
    public void test3() {
        Expression s = r.sum(r.sum(r.minusOne(), r.product(j, r.newIntConstant(-3))), r.product(r.newIntConstant(2), i));
        assertEquals("-1+2*i+-3*j", s.toString());
    }
}
