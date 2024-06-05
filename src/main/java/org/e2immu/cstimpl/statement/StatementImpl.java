package org.e2immu.cstimpl.statement;

import org.e2immu.annotation.Fluent;
import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.expression.AnnotationExpression;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.statement.Statement;
import org.e2immu.cstimpl.element.ElementImpl;
import org.e2immu.cstimpl.output.OutputBuilderImpl;
import org.e2immu.cstimpl.output.Space;
import org.e2immu.cstimpl.output.Symbol;
import org.e2immu.cstimpl.output.Text;

import java.util.List;

public abstract class StatementImpl extends ElementImpl implements Statement {

    private final List<Comment> comments;
    private final List<AnnotationExpression> annotations;
    private final Source source;
    private final int complexity;
    private final String label;

    protected StatementImpl(List<Comment> comments,
                            Source source,
                            List<AnnotationExpression> annotations,
                            int complexity,
                            String label) {
        this.complexity = complexity;
        this.source = source;
        this.annotations = annotations;
        this.comments = comments;
        this.label = label;
    }

    protected StatementImpl() {
        this(List.of(), null, List.of(), 1, null);
    }

    protected OutputBuilder outputBuilderWithLabel() {
        OutputBuilder ob = new OutputBuilderImpl();
        if (label != null) {
            ob.add(Space.ONE).add(new Text(label)).add(Symbol.COLON_LABEL).add(Space.ONE_IS_NICE_EASY_SPLIT);
        }
        return ob;
    }

    @Override
    public Source source() {
        return source;
    }

    @Override
    public List<AnnotationExpression> annotations() {
        return annotations;
    }

    @Override
    public List<Comment> comments() {
        return comments;
    }

    @Override
    public int complexity() {
        return complexity;
    }

    @Override
    public String label() {
        return label;
    }

    public static abstract class Builder extends ElementImpl.Builder implements Statement.Builder {
        protected String label;

        @Fluent
        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }
    }
}
