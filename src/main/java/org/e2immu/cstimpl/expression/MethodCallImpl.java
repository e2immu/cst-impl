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
import org.e2immu.cstapi.output.element.Guide;
import org.e2immu.cstapi.output.element.QualifiedName;
import org.e2immu.cstapi.output.element.ThisName;
import org.e2immu.cstapi.output.element.TypeName;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.This;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.element.ElementImpl;
import org.e2immu.cstimpl.expression.util.ExpressionComparator;
import org.e2immu.cstimpl.expression.util.PrecedenceEnum;
import org.e2immu.cstimpl.output.*;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.e2immu.cstimpl.output.QualifiedNameImpl.Required.NO_METHOD;
import static org.e2immu.cstimpl.output.QualifiedNameImpl.Required.YES;

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

        public Builder() {

        }

        public Builder(MethodCall mc) {
            super(mc);
            object = mc.object();
            methodInfo = mc.methodInfo();
            parameterExpressions = mc.parameterExpressions();
            objectIsImplicit = mc.objectIsImplicit();
            concreteReturnType = mc.concreteReturnType();
            modificationTimes = mc.modificationTimes();
        }

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
    public MethodCall withParameterExpressions(List<Expression> parameterExpressions) {
        return new MethodCallImpl(source(), comments(), object, objectIsImplicit, methodInfo, parameterExpressions,
                concreteReturnType, modificationTimes);
    }

    @Override
    public ParameterizedType parameterizedType() {
        return concreteReturnType;
    }

    @Override
    public Precedence precedence() {
        return PrecedenceEnum.ARRAY_ACCESS;
    }

    @Override
    public int order() {
        return ExpressionComparator.ORDER_METHOD;
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
        return print(qualification, null);
    }

    // will come directly here only from this method (chaining of method calls produces a guide)
    public OutputBuilder print(Qualification qualification, GuideImpl.GuideGenerator guideGenerator) {
        OutputBuilder outputBuilder = new OutputBuilderImpl();
        boolean last = false;
        boolean start = false;
        GuideImpl.GuideGenerator gg = null;
        if (objectIsImplicit && qualification.doNotQualifyImplicit()) {
            outputBuilder.add(new TextImpl(methodInfo.name()));
        } else {
            VariableExpression ve;
            MethodCall methodCall;
            TypeExpression typeExpression;
            if ((methodCall = object.asInstanceOf(MethodCall.class)) != null) {
                // chaining!
                if (guideGenerator == null) {
                    gg = GuideImpl.defaultGuideGenerator();
                    last = true;
                } else {
                    gg = guideGenerator;
                }
                outputBuilder.add(((MethodCallImpl) methodCall).print(qualification, gg)); // recursive call
                outputBuilder.add(gg.mid());
                outputBuilder.add(SymbolEnum.DOT);
                outputBuilder.add(new TextImpl(methodInfo.name()));
            } else if ((typeExpression = object.asInstanceOf(TypeExpression.class)) != null) {
                /*
                we may or may not need to write the type here.
                (we check methodInspection is set, because of debugOutput)
                 */
                assert methodInfo.isStatic();
                TypeInfo typeInfo = typeExpression.parameterizedType().typeInfo();
                TypeName typeName = TypeNameImpl.typeName(typeInfo, qualification.qualifierRequired(typeInfo));
                outputBuilder.add(new QualifiedNameImpl(methodInfo.name(), typeName,
                        qualification.qualifierRequired(methodInfo) ? YES : NO_METHOD));
                if (guideGenerator != null) start = true;
            } else if ((ve = object.asInstanceOf(VariableExpression.class)) != null &&
                       ve.variable() instanceof This thisVar) {
                //     (we check methodInspection is set, because of debugOutput)
                assert !methodInfo.isStatic() : "Have a static method with scope 'this'? "
                                                + methodInfo.fullyQualifiedName() + "; this "
                                                + thisVar.typeInfo().fullyQualifiedName();
                TypeName typeName = TypeNameImpl.typeName(thisVar.typeInfo(),
                        qualification.qualifierRequired(thisVar.typeInfo()));
                ThisName thisName = new ThisNameImpl(thisVar.writeSuper(), typeName,
                        qualification.qualifierRequired(thisVar));
                outputBuilder.add(new QualifiedNameImpl(methodInfo.name(), thisName,
                        qualification.qualifierRequired(methodInfo) ? YES : NO_METHOD));
                if (guideGenerator != null) start = true;
            } else {
                // next level is NOT a gg; if gg != null we're at the start of the chain
                outputBuilder.add(outputInParenthesis(qualification, precedence(), object));
                if (guideGenerator != null) outputBuilder.add(guideGenerator.start());
                outputBuilder.add(SymbolEnum.DOT);
                outputBuilder.add(new TextImpl(methodInfo.name()));
            }
        }

        if (parameterExpressions.isEmpty()) {
            outputBuilder.add(SymbolEnum.OPEN_CLOSE_PARENTHESIS);
        } else {
            outputBuilder
                    .add(SymbolEnum.LEFT_PARENTHESIS)
                    .add(parameterExpressions.stream()
                            .map(expression -> expression.print(qualification))
                            .collect(OutputBuilderImpl.joining(SymbolEnum.COMMA)))
                    .add(SymbolEnum.RIGHT_PARENTHESIS);
        }
        if (start) {
            outputBuilder.add(guideGenerator.start());
        }
        if (last) {
            outputBuilder.add(gg.end());
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
}
