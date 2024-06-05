package org.e2immu.cstimpl.element;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.expression.AnnotationExpression;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstimpl.output.QualificationImpl;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementImpl implements Element {

    public record TypeReference(TypeInfo typeInfo, boolean explicit) implements Element.TypeReference {
    }

    @Override
    public String toString() {
        return print(QualificationImpl.SIMPLE_NAMES).toString();
    }

    public abstract static class Builder implements Element.Builder {
        protected final List<Comment> comments = new ArrayList<>();
        protected final List<AnnotationExpression> annotations = new ArrayList<>();
        protected Source source;

        @Override
        public Builder setSource(Source source) {
            this.source = source;
            return this;
        }

        @Override
        public Builder addComment(Comment comment) {
            this.comments.add(comment);
            return this;
        }

        @Override
        public Builder addComments(List<Comment> comments) {
            this.comments.addAll(comments);
            return this;
        }

        @Override
        public Builder addAnnotation(AnnotationExpression annotation) {
            this.annotations.add(annotation);
            return this;
        }

        @Override
        public Builder addAnnotations(List<AnnotationExpression> annotations) {
            this.annotations.addAll(annotations);
            return this;
        }


    }
}
