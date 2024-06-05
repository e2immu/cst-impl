package org.e2immu.cstimpl.info;

import org.e2immu.annotation.Fluent;
import org.e2immu.annotation.Modified;
import org.e2immu.annotation.NotNull;
import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.expression.AnnotationExpression;

import java.util.List;

public interface Inspection {

    enum Access {
        PRIVATE(0), PACKAGE(1), PROTECTED(2), PUBLIC(3);

        private final int level;

        Access(int level) {
            this.level = level;
        }

        public Access combine(Access other) {
            if (level < other.level) return this;
            return other;
        }

        public boolean le(Access other) {
            return level <= other.level;
        }
    }

    Access access();

    default boolean accessNotYetComputed() {
        return access() == null;
    }

    List<Comment> comments();

    Source source();

    boolean isSynthetic();

    default boolean isPublic() {
        return access() == Access.PUBLIC;
    }

    default boolean isPrivate() {
        return access() == Access.PRIVATE;
    }

    default boolean isProtected() {
        return access() == Access.PROTECTED;
    }

    default boolean isPackagePrivate() {
        return access() == Access.PACKAGE;
    }

    @NotNull(content = true)
    List<AnnotationExpression> annotations();
}
