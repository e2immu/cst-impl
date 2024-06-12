package org.e2immu.cstimpl.expression;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.MethodCall;
import org.e2immu.cstapi.expression.Precedence;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.element.ElementImpl;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MethodCallImpl extends ExpressionImpl implements MethodCall {
    private final Expression object;
    private final boolean objectIsImplicit;
    private final MethodInfo methodInfo;
    private final List<Expression> parameterExpressions;
    private final ParameterizedType concreteReturnType;
    private final String modificationTimes;

    public MethodCallImpl(Source source, List<Comment> comments,
                          Expression object, boolean objectIsImplicit, MethodInfo methodInfo,
                          List<Expression> parameterExpressions, ParameterizedType concreteReturnType,
                          String modificationTimes) {
        super(comments, source, object.complexity()
                                + methodInfo.complexity()
                                + parameterExpressions.stream().mapToInt(Expression::complexity).sum());
        this.object = object;
        this.objectIsImplicit = objectIsImplicit;
        this.parameterExpressions = parameterExpressions;
        this.concreteReturnType = concreteReturnType;
        this.methodInfo = methodInfo;
        this.modificationTimes = modificationTimes;
    }

    public static class Builder extends ElementImpl.Builder<MethodCall.Builder> implements MethodCall.Builder {
        private Expression object;
        private MethodInfo methodInfo;
        private List<Expression> parameterExpressions;
        private boolean objectIsImplicit;
        private ParameterizedType concreteReturnType;
        private String modificationTimes;

        @Override
        public MethodCall build() {
            assert parameterExpressions != null : "Must set parameter expressions!";
            assert object != null : "Must set object, even if it is the implicit 'this', of call to "
                                    + methodInfo;
            assert concreteReturnType != null : "Must set the concrete return type of call to " + methodInfo;
            return new MethodCallImpl(source, comments, object, objectIsImplicit, methodInfo,
                    List.copyOf(parameterExpressions), concreteReturnType, modificationTimes);
        }

        @Override
        public MethodCall.Builder setObject(Expression object) {
            this.object = object;
            return this;
        }

        @Override
        public MethodCall.Builder setMethodInfo(MethodInfo methodInfo) {
            this.methodInfo = methodInfo;
            return this;
        }

        @Override
        public MethodCall.Builder setModificationTimes(String modificationTimes) {
            this.modificationTimes = modificationTimes;
            return this;
        }

        @Override
        public MethodCall.Builder setParameterExpressions(List<Expression> expressions) {
            this.parameterExpressions = expressions;
            return this;
        }

        @Override
        public MethodCall.Builder setObjectIsImplicit(boolean objectIsImplicit) {
            this.objectIsImplicit = objectIsImplicit;
            return this;
        }

        @Override
        public MethodCall.Builder setConcreteReturnType(ParameterizedType returnType) {
            this.concreteReturnType = returnType;
            return this;
        }
    }

    @Override
    public MethodInfo methodInfo() {
        return methodInfo;
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
    public String modificationTimes() {
        return modificationTimes;
    }

    @Override
    public boolean objectIsImplicit() {
        return objectIsImplicit;
    }

    @Override
    public ParameterizedType concreteReturnType() {
        return concreteReturnType;
    }

    @Override
    public ParameterizedType parameterizedType() {
        return concreteReturnType;
    }

    @Override
    public Precedence precedence() {
        return null;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public int internalCompareTo(Expression expression) {
        return 0;
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
