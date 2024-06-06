package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.analysis.Property;
import org.e2immu.cstapi.analysis.Value;
import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.AnnotationExpression;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.ParameterInfo;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.statement.Block;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.analysis.PropertyImpl;
import org.e2immu.cstimpl.analysis.ValueImpl;
import org.e2immu.support.EventuallyFinal;
import org.e2immu.support.SetOnceMap;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MethodInfoImpl extends InfoImpl implements MethodInfo {

    public enum MethodType {
        CONSTRUCTOR(true), COMPACT_CONSTRUCTOR(true), SYNTHETIC_CONSTRUCTOR(true),
        STATIC_BLOCK(false), DEFAULT_METHOD(false), STATIC_METHOD(false),
        ABSTRACT_METHOD(false), METHOD(false);
        final boolean constructor;

        MethodType(boolean constructor) {
            this.constructor = constructor;
        }

        public boolean isConstructor() {
            return constructor;
        }
    }

    private final TypeInfo typeInfo; // back reference, only @ContextClass after...
    private final String name;
    private final MethodType methodType;
    private final EventuallyFinal<MethodInspection> inspection = new EventuallyFinal<>();

    public MethodInfoImpl(MethodType methodType,
                          String name,
                          TypeInfo typeInfo) {
        this.name = name;
        this.methodType = methodType;
        this.typeInfo = typeInfo;
        inspection.setVariable(new MethodInspectionImpl.Builder());
    }

    public MethodInspectionImpl.Builder builder() {
        if (inspection.isVariable()) return (MethodInspectionImpl.Builder) inspection.get();
        throw new UnsupportedOperationException();
    }

    public void endOfInspection() {
        inspection.setFinal(((MethodInspectionImpl.Builder) inspection.get()).build());
    }

    @Override
    public boolean isConstructor() {
        return methodType.isConstructor();
    }

    @Override
    public TypeInfo primaryType() {
        return typeInfo.primaryType();
    }

    @Override
    public boolean methodAnalysisIsSet() {
        return false;
    }


    @Override
    public boolean isVoid() {
        return inspection.get().returnType().isVoid();
    }

    @Override
    public int complexity() {
        Block methodBody = inspection.get().methodBody();
        return methodBody == null ? 10 : methodBody.complexity();
    }

    @Override
    public List<Comment> comments() {
        return inspection.get().comments();
    }

    @Override
    public Source source() {
        return inspection.get().source();
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
        Block methodBody = inspection.get().methodBody();
        return methodBody == null ? Stream.empty() : methodBody.variables(descendMode);
    }

    @Override
    public Stream<TypeReference> typesReferenced() {
        throw new UnsupportedOperationException(); // FIXME
    }

    @Override
    public boolean complexityGreaterThanCOMPLEXITY_METHOD_WITHOUT_CODE() {
        return false;
    }


    @Override
    public boolean isPostfix() {
        return inspection.get().operatorType() == MethodInspection.OperatorType.POSTFIX;
    }

    @Override
    public boolean isInfix() {
        return inspection.get().operatorType() == MethodInspection.OperatorType.INFIX;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String fullyQualifiedName() {
        return inspection.get().fullyQualifiedName();
    }

    @Override
    public TypeInfo typeInfo() {
        return typeInfo;
    }

    @Override
    public boolean isDefault() {
        return methodType == MethodType.DEFAULT_METHOD;
    }

    public boolean isCompactConstructor() {
        return methodType == MethodType.COMPACT_CONSTRUCTOR;
    }

    public boolean isSyntheticConstructor() {
        return methodType == MethodType.SYNTHETIC_CONSTRUCTOR;
    }

    public boolean isStaticBlock() {
        return methodType == MethodType.STATIC_BLOCK;
    }

    @Override
    public boolean isStatic() {
        return methodType == MethodType.STATIC_METHOD || methodType == MethodType.STATIC_BLOCK;
    }

    @Override
    public ParameterizedType returnType() {
        return inspection.get().returnType();
    }

    @Override
    public Set<MethodInfo> topOfOverloadingHierarchy() {
        return Set.of();
    }

    @Override
    public List<ParameterInfo> parameters() {
        return List.of();
    }

    @Override
    public boolean isOverloadOf(MethodInfo methodInfo) {
        return false;
    }

    @Override
    public boolean isOverloadOfJLOEquals() {
        return parameters().size() == 1 && "equals".equals(name);
    }

    @Override
    public Set<MethodInfo> overrides() {
        return inspection.get().overrides();
    }

    @Override
    public boolean isPublic() {
        return inspection.get().isPublic();
    }

    @Override
    public boolean isPubliclyAccessible() {
        if (!isPublic()) return false;
        return typeInfo.isPublic();
    }

    @Override
    public boolean isModifying() {
        return analysedOrDefault(PropertyImpl.MODIFIED_METHOD, ValueImpl.FALSE).isTrue();
    }

    @Override
    public boolean isFluent() {
        return analysedOrDefault(PropertyImpl.FLUENT, ValueImpl.FALSE).isTrue();
    }

    @Override
    public List<AnnotationExpression> annotations() {
        return inspection.get().annotations();
    }
}
