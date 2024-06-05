package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.expression.Expression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSum extends CommonTest {


    @Test
    public void test1() {
        Expression s = r.sum(r.one(), i);
        assertEquals("1+i", s.toString());
        Expression s2 = r.sum(r.newInt(2), s);
        assertEquals("3+i", s2.toString());
    }

    @Test
    public void test2() {
        Expression s = r.sum(r.one(), r.sum(i, r.newInt(3)));
        assertEquals("4+i", s.toString());
        Expression s2 = r.sum(r.sum(r.newInt(3), r.newInt(2)), r.sum(s, r.newInt(-9)));
        assertEquals("i", s2.toString());
        Expression s3 = r.sum(r.sum(s2, r.newInt(3)), r.negate(s2));
        assertEquals("3", s3.toString());
    }

    @Test
    public void test3() {
        Expression s = r.sum(r.sum(r.minusOne(), r.product(j, r.newInt(-3))), r.product(r.newInt(2), i));
        assertEquals("-1+2*i+-3*j", s.toString());
    }
}
