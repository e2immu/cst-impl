package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.ParameterInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.variable.DescendModeEnum;
import org.e2immu.support.EventuallyFinal;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ParameterInfoImpl implements ParameterInfo {
    private final int index;
    private final String name;
    private final MethodInfo methodInfo;
    private final ParameterizedType parameterizedType;
    private final EventuallyFinal<ParameterInspection> inspection;

    public ParameterInfoImpl(MethodInfo methodInfo, int index, String name, ParameterizedType parameterizedType) {
        this.methodInfo = methodInfo;
        this.index = index;
        this.name = name;
        this.parameterizedType = parameterizedType;
        inspection = new EventuallyFinal<>();
        inspection.setVariable(new ParameterInspectionImpl.Builder());
    }

    public ParameterInspectionImpl.Builder builder() {
        if (inspection.isVariable()) return (ParameterInspectionImpl.Builder) inspection.get();
        throw new UnsupportedOperationException();
    }

    public void endOfInspection() {
        inspection.setFinal(((ParameterInspectionImpl.Builder) inspection.get()).build());
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isVarArgs() {
        return inspection.get().isVarArgs();
    }

    @Override
    public String fullyQualifiedName() {
        return methodInfo.fullyQualifiedName() + ":" + index + ":" + name;
    }

    @Override
    public String simpleName() {
        return name;
    }

    @Override
    public ParameterizedType parameterizedType() {
        return parameterizedType;
    }

    @Override
    public boolean isLocal() {
        return false;
    }

    @Override
    public int complexity() {
        return 0;
    }

    @Override
    public List<Comment> comments() {
        return List.of();
    }

    @Override
    public Source source() {
        return null;
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
        return Stream.of(this);
    }

    @Override
    public Stream<TypeReference> typesReferenced() {
        return Stream.empty();
    }

    @Override
    public Stream<Variable> variableStreamDescend() {
        return variables(DescendModeEnum.YES);
    }

    @Override
    public Stream<Variable> variableStreamDoNotDescend() {
        return variables(DescendModeEnum.NO);
    }
}
