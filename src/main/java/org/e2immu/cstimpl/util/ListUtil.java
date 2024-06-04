package org.e2immu.cstimpl.util;

import org.e2immu.annotation.NotModified;

import java.util.Iterator;
import java.util.List;

public class ListUtil {
    @NotModified
    public static <T extends Comparable<? super T>> int compare(@NotModified List<T> values1,
                                                                @NotModified List<T> values2) {
        Iterator<T> it2 = values2.iterator();
        for (T t1 : values1) {
            if (!it2.hasNext()) return 1;
            T t2 = it2.next();
            int c = t1.compareTo(t2);
            if (c != 0) return c;
        }
        if (it2.hasNext()) return -1;
        return 0;
    }

}
