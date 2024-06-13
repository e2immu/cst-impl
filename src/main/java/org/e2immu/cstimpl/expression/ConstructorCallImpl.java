package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.*;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.Diamond;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.element.ElementImpl;
import org.e2immu.cstimpl.expression.util.ExpressionComparator;
import org.e2immu.cstimpl.expression.util.InternalCompareToException;
import org.e2immu.cstimpl.expression.util.PrecedenceEnum;
import org.e2immu.cstimpl.output.KeywordImpl;
import org.e2immu.cstimpl.output.OutputBuilderImpl;
import org.e2immu.cstimpl.output.SpaceEnum;
import org.e2immu.cstimpl.output.SymbolEnum;
import org.e2immu.cstimpl.type.DiamondEnum;
import org.e2immu.cstimpl.util.ListUtil;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ConstructorCallImpl extends ExpressionImpl implements ConstructorCall {
    private final MethodInfo constructor;
    private final Diamond diamond;
    private final Expression object;
    private final List<Expression> parameterExpressions;
    private final ArrayInitializer arrayInitializer;
    private final TypeInfo anonymousClass;
    private final ParameterizedType concreteReturnType;

    public ConstructorCallImpl(List<Comment> comments, Source source, MethodInfo constructor,
                               ParameterizedType concreteReturnType,
                               Diamond diamond, Expression object, List<Expression> expressions,
                               ArrayInitializer arrayInitializer, TypeInfo anonymousClass) {
        super(comments, source, 1 + (object == null ? 0 : object.complexity())
                                + expressions.stream().mapToInt(Expression::complexity).sum());
        this.constructor = constructor;
        this.diamond = diamond;
        this.object = object;
        parameterExpressions = expressions;
        this.arrayInitializer = arrayInitializer;
        this.anonymousClass = anonymousClass;
        this.concreteReturnType = concreteReturnType;
    }

    public static class Builder extends ElementImpl.Builder<ConstructorCall.Builder> implements ConstructorCall.Builder {
        private MethodInfo constructor;
        private Diamond diamond;
        private Expression object;
        private List<Expression> parameterExpressions;
        private ArrayInitializer arrayInitializer;
        private TypeInfo anonymousClass;
        private ParameterizedType concreteReturnType;

        @Override
        public ConstructorCall build() {
            return new ConstructorCallImpl(comments, source, constructor, concreteReturnType, diamond, object,
                    List.copyOf(parameterExpressions), arrayInitializer, anonymousClass);
        }

        @Override
        public Builder setObject(Expression object) {
            this.object = object;
            return this;
        }

        @Override
        public Builder setDiamond(Diamond diamond) {
            this.diamond = diamond;
            return this;
        }

        @Override
        public Builder setConstructor(MethodInfo constructor) {
            this.constructor = constructor;
            return this;
        }

        @Override
        public Builder setAnonymousClass(TypeInfo anonymousClass) {
            this.anonymousClass = anonymousClass;
            return this;
        }

        @Override
        public Builder setArrayInitializer(ArrayInitializer arrayInitializer) {
            this.arrayInitializer = arrayInitializer;
            return this;
        }

        @Override
        public Builder setParameterExpressions(List<Expression> expressions) {
            this.parameterExpressions = expressions;
            return this;
        }

        @Override
        public Builder setConcreteReturnType(ParameterizedType returnType) {
            this.concreteReturnType = returnType;
            return this;
        }
    }

    @Override
    public MethodInfo constructor() {
        return constructor;
    }

    @Override
    public Expression object() {
        return object;
    }

    @Override
    public List<Expression> parameterExpressions() {
        return parameterExpressions;
    }

    @Override
    public TypeInfo anonymousClass() {
        return anonymousClass;
    }

    @Override
    public ArrayInitializer arrayInitializer() {
        return arrayInitializer;
    }

    @Override
    public ParameterizedType parameterizedType() {
        return concreteReturnType;
    }

    @Override
    public Precedence precedence() {
        return PrecedenceEnum.UNARY;
    }

    @Override
    public int order() {
        return ExpressionComparator.ORDER_NEW_INSTANCE;
    }

    @Override
    public int internalCompareTo(Expression expression) {
        if (expression instanceof ConstructorCall cc) {
            int c = constructor.fullyQualifiedName().compareTo(cc.constructor().fullyQualifiedName());
            if (c != 0) return c;
            int d = ListUtil.compare(parameterExpressions, cc.parameterExpressions());
            if (d != 0) return d;
            if (object == null && cc.object() != null) return -1;
            if (object != null && cc.object() == null) return 1;
            if (object != null) {
                return object.compareTo(cc.object());
            }
            return 0;
        }
        throw new InternalCompareToException();
    }

    @Override
    public void visit(Predicate<Element> predicate) {
        if (predicate.test(this)) {
            if (object != null) object.visit(predicate);
            parameterExpressions.forEach(p -> p.visit(predicate));
        }
    }

    @Override
    public void visit(Visitor visitor) {
        if (visitor.beforeExpression(this)) {
            if (object != null) object.visit(visitor);
            parameterExpressions.forEach(p -> p.visit(visitor));
        }
        visitor.afterExpression(this);
    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        OutputBuilder outputBuilder = new OutputBuilderImpl();
        if (object != null) {
            outputBuilder.add(outputInParenthesis(qualification, precedence(), object));
            outputBuilder.add(SymbolEnum.DOT);
        }
        if (constructor != null || anonymousClass != null) {
            outputBuilder.add(KeywordImpl.NEW).add(SpaceEnum.ONE)
                    .add(concreteReturnType.copyWithoutArrays().print(qualification, false, diamond));
            //      if (arrayInitializer == null) {
            if (concreteReturnType.arrays() > 0) {
                for (int i = 0; i < concreteReturnType.arrays(); i++) {
                    if (i < parameterExpressions.size()) {
                        outputBuilder.add(SymbolEnum.LEFT_BRACKET);
                        Expression size = parameterExpressions.get(i);
                        if (!(size.isEmpty())) {
                            outputBuilder.add(size.print(qualification));
                        }
                        outputBuilder.add(SymbolEnum.RIGHT_BRACKET);
                    } else {
                        outputBuilder.add(SymbolEnum.OPEN_CLOSE_BRACKETS);
                    }
                }
            } else {
                if (parameterExpressions.isEmpty()) {
                    outputBuilder.add(SymbolEnum.OPEN_CLOSE_PARENTHESIS);
                } else {
                    outputBuilder
                            .add(SymbolEnum.LEFT_PARENTHESIS)
                            .add(parameterExpressions.stream().map(expression -> expression.print(qualification))
                                    .collect(OutputBuilderImpl.joining(SymbolEnum.COMMA)))
                            .add(SymbolEnum.RIGHT_PARENTHESIS);
                }
            }
            //    }
        }
        if (anonymousClass != null) {
            outputBuilder.add(anonymousClass.print(qualification, true));
        }
        if (arrayInitializer != null) {
            outputBuilder.add(arrayInitializer.print(qualification));
        }
        return outputBuilder;
    }

    @Override
    public Stream<Variable> variables(DescendMode descendMode) {
        return Stream.empty();
    }

    @Override
    public Stream<Element.TypeReference> typesReferenced() {
        return Stream.empty();
    }

    @Override
    public ConstructorCall withParameterExpressions(List<Expression> newParameterExpressions) {
        return null;
    }

    @Override
    public Diamond diamond() {
        return diamond;
    }
}
