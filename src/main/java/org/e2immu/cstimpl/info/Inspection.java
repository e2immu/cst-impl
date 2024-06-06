package org.e2immu.cstimpl.info;

import org.e2immu.annotation.NotNull;
import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.expression.AnnotationExpression;

import java.util.List;

public interface Inspection {

    interface Access {

        int level();

        Access combine(Access other);

        default boolean le(Access other) {
            return level() <= other.level();
        }

        boolean isPublic();

        boolean isPrivate();

        boolean isProtected();

        boolean isPackage();
    }

    Access access();

    default boolean accessNotYetComputed() {
        return access() == null;
    }

    List<Comment> comments();

    Source source();

    boolean isSynthetic();

    default boolean isPublic() {
        return access().isPublic();
    }

    default boolean isPrivate() {
        return access().isPrivate();
    }

    default boolean isProtected() {
        return access().isProtected();
    }

    default boolean isPackagePrivate() {
        return access().isPackage();
    }

    @NotNull(content = true)
    List<AnnotationExpression> annotations();
}
