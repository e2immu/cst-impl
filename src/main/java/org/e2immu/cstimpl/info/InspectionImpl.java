package org.e2immu.cstimpl.info;

import org.e2immu.annotation.Fluent;
import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.expression.AnnotationExpression;

import java.util.List;

public class InspectionImpl implements Inspection {
    private final Access access;
    private final List<Comment> comments;
    private final Source source;
    private final boolean synthetic;
    private final List<AnnotationExpression> annotations;

    public InspectionImpl(Access access,
                          List<Comment> comments,
                          Source source,
                          boolean synthetic,
                          List<AnnotationExpression> annotations) {
        this.access = access;
        this.comments = comments;
        this.source = source;
        this.synthetic = synthetic;
        this.annotations = annotations;
    }

    @Override
    public Access access() {
        return access;
    }

    @Override
    public List<Comment> comments() {
        return comments;
    }

    @Override
    public Source source() {
        return source;
    }

    @Override
    public boolean isSynthetic() {
        return synthetic;
    }

    @Override
    public List<AnnotationExpression> annotations() {
        return annotations;
    }

    public static class Builder implements Inspection {

        private Access access;
        private List<Comment> comments;
        private Source source;
        private boolean synthetic;
        private List<AnnotationExpression> annotations;

        @Fluent
        public Builder setAccess(Access access) {
            this.access = access;
            return this;
        }

        @Fluent
        public Builder setAnnotations(List<AnnotationExpression> annotations) {
            this.annotations = annotations;
            return this;
        }

        @Fluent
        public Builder setComments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        @Fluent
        public Builder setSource(Source source) {
            this.source = source;
            return this;
        }

        @Fluent
        public Builder setSynthetic(boolean synthetic) {
            this.synthetic = synthetic;
            return this;
        }

        @Override
        public Access access() {
            return access;
        }

        @Override
        public List<Comment> comments() {
            return comments;
        }

        @Override
        public Source source() {
            return source;
        }

        @Override
        public boolean isSynthetic() {
            return synthetic;
        }

        @Override
        public List<AnnotationExpression> annotations() {
            return annotations;
        }

        public Inspection build() {
            return new InspectionImpl(access, comments, source, isSynthetic(), annotations);
        }
    }
}
