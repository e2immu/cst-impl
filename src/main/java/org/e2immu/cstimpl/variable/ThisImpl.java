package org.e2immu.cstimpl.variable;

import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.This;
import org.e2immu.cstapi.variable.Variable;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class ThisImpl extends VariableImpl implements This {

    public ThisImpl(TypeInfo typeInfo) {
        super(typeInfo.asSimpleParameterizedType());
    }

    @Override
    public TypeInfo typeInfo() {
        return parameterizedType().typeInfo();
    }

    @Override
    public String fullyQualifiedName() {
        return parameterizedType().typeInfo().fullyQualifiedName();
    }

    @Override
    public String simpleName() {
        return "this";
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
    public Stream<TypeReference> typesReferenced() {
        return Stream.empty();
    }
}
