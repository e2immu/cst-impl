package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Visitor;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.info.Access;
import org.e2immu.cstapi.info.FieldInfo;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.analysis.PropertyImpl;
import org.e2immu.support.EventuallyFinal;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class FieldInfoImpl extends InfoImpl implements FieldInfo {

    private final String name;
    private final boolean isStatic;
    private final ParameterizedType type;
    private final String fullyQualifiedName;
    private final TypeInfo owner;
    private final EventuallyFinal<FieldInspection> inspection;

    public FieldInfoImpl(String name, boolean isStatic, ParameterizedType type, TypeInfo owner) {
        this.name = name;
        this.isStatic = isStatic;
        this.type = type;
        this.fullyQualifiedName = owner.fullyQualifiedName() + "." + name;
        this.owner = owner;
        inspection = new EventuallyFinal<>();
        inspection.setVariable(new FieldInspectionImpl.Builder(this));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public TypeInfo owner() {
        return owner;
    }

    @Override
    public ParameterizedType type() {
        return type;
    }

    @Override
    public String fullyQualifiedName() {
        return fullyQualifiedName;
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public boolean isFinal() {
        return inspection.get().fieldModifiers().contains(FieldModifierEnum.FINAL);
    }

    @Override
    public boolean isTransient() {
        return inspection.get().fieldModifiers().contains(FieldModifierEnum.TRANSIENT);
    }

    @Override
    public boolean isVolatile() {
        return inspection.get().fieldModifiers().contains(FieldModifierEnum.VOLATILE);
    }

    @Override
    public boolean isPropertyNotNull() {
        return analysed(PropertyImpl.FIELD_NOT_NULL).isTrue();
    }

    @Override
    public Access access() {
        return inspection.get().access();
    }

    @Override
    public boolean isPropertyFinal() {
        return analysed(PropertyImpl.FIELD_FINAL).isTrue();
    }

    @Override
    public int complexity() {
        return 1 + inspection.get().initializer().complexity();
    }

    @Override
    public void visit(Predicate<Element> predicate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(Visitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Variable> variables(DescendMode descendMode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<TypeReference> typesReferenced() {
        Expression initializer = inspection.get().initializer();
        return Stream.concat(type.typesReferenced(), initializer == null ? Stream.of() : initializer.typesReferenced());
    }

    @Override
    public FieldInfo.Builder builder() {
        assert inspection.isVariable();
        return (FieldInfo.Builder) inspection.get();
    }

    public void commit(FieldInspectionImpl fieldInspection) {
        inspection.setFinal(fieldInspection);
    }
}
