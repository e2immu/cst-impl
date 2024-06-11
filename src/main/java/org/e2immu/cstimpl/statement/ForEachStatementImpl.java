package org.e2immu.cstimpl.statement;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.statement.Block;
import org.e2immu.cstapi.statement.ForEachStatement;
import org.e2immu.cstapi.statement.LocalVariableCreation;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ForEachStatementImpl extends StatementImpl implements ForEachStatement {
    private final LocalVariableCreation initializer;
    private final Expression expression;
    private final Block block;

    public ForEachStatementImpl(Source source,
                                List<Comment> comments,
                                String label,
                                LocalVariableCreation initializer,
                                Expression expression, Block block) {
        super(comments, source, List.of(), 0, null);
        this.initializer = initializer;
        this.expression = expression;
        this.block = block;
    }

    public static class Builder extends StatementImpl.Builder<ForEachStatement.Builder> implements ForEachStatement.Builder {
        private LocalVariableCreation initializer;
        private Expression expression;
        private Block block;

        @Override
        public ForEachStatement.Builder setInitializer(LocalVariableCreation initializer) {
            this.initializer = initializer;
            return this;
        }

        @Override
        public ForEachStatement.Builder setExpression(Expression expression) {
            this.expression = expression;
            return this;
        }

        @Override
        public ForEachStatement.Builder setBlock(Block block) {
            this.block = block;
            return this;
        }

        @Override
        public ForEachStatement build() {
            return new ForEachStatementImpl(source, comments, label, initializer, expression, block);
        }
    }

    @Override
    public LocalVariableCreation initializer() {
        return initializer;
    }

    @Override
    public Expression expression() {
        return expression;
    }

    @Override
    public Block block() {
        return block;
    }

    @Override
    public void visit(Predicate<Element> predicate) {

    }

    @Override
    public void visit(Visitor visitor) {

    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        return null;
    }

    @Override
    public Stream<Variable> variables(DescendMode descendMode) {
        return Stream.empty();
    }

    @Override
    public Stream<Element.TypeReference> typesReferenced() {
        return Stream.empty();
    }
}
