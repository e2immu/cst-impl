package org.e2immu.cstimpl.type;

import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.runtime.PredefinedWithoutParameterizedType;
import org.e2immu.cstapi.runtime.Runtime;
import org.e2immu.cstapi.type.Diamond;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.type.TypeParameter;
import org.e2immu.cstapi.type.Wildcard;

import java.util.List;
import java.util.stream.Stream;

public class ParameterizedTypeImpl implements ParameterizedType {

    private final TypeParameter typeParameter;
    private final TypeInfo typeInfo;
    private final int arrays;
    private final Wildcard wildcard;

    public ParameterizedTypeImpl(TypeParameter typeParameter, int arrays) {
        this(null, typeParameter, arrays, null);
    }

    public ParameterizedTypeImpl(TypeInfo typeInfo) {
        this(typeInfo, null, 0, null);
    }

    public ParameterizedTypeImpl(TypeInfo typeInfo, int arrays) {
        this(typeInfo, null, arrays, null);
    }

    public ParameterizedTypeImpl(TypeInfo typeInfo, TypeParameter typeParameter, int arrays, Wildcard wildcard) {
        this.typeParameter = typeParameter;
        this.typeInfo = typeInfo;
        this.arrays = arrays;
        this.wildcard = wildcard;
    }

    public OutputBuilder print(Qualification qualification) {
        return ParameterizedTypePrinter.print(qualification, this,
                false, DiamondImpl.SHOW_ALL, false);
    }

    @Override
    public Wildcard wildcard() {
        return wildcard;
    }

    @Override
    public TypeParameter typeParameter() {
        return typeParameter;
    }

    @Override
    public TypeInfo typeInfo() {
        return typeInfo;
    }

    @Override
    public int arrays() {
        return arrays;
    }

    @Override
    public List<ParameterizedType> parameters() {
        return parameters();
    }

    @Override
    public String fullyQualifiedName() {
        return "";
    }

    public OutputBuilder print(Qualification qualification, boolean varArgs, Diamond diamond) {
        return ParameterizedTypePrinter.print(qualification, this, varArgs, diamond, false);
    }

    @Override
    public Stream<Element.TypeReference> typesReferenced() {
        return Stream.empty();
    }

    @Override
    public ParameterizedType erased() {
        return null;
    }

    @Override
    public ParameterizedType copyWithArrays(int arrays) {
        return null;
    }

    @Override
    public ParameterizedType copyWithOneFewerArrays() {
        return null;
    }

    @Override
    public ParameterizedType ensureBoxed(PredefinedWithoutParameterizedType predefined) {
        return null;
    }

    @Override
    public ParameterizedType copyWithFewerArrays(int n) {
        return null;
    }

    @Override
    public ParameterizedType copyWithoutArrays() {
        return null;
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isTypeParameter() {
        return false;
    }

    @Override
    public boolean isVoidOrJavaLangVoid() {
        return false;
    }

    @Override
    public boolean isBooleanOrBoxedBoolean() {
        return false;
    }

    @Override
    public boolean isVoid() {
        return false;
    }

    @Override
    public boolean isPrimitiveExcludingVoid() {
        return false;
    }

    @Override
    public boolean isPrimitiveStringClass() {
        return false;
    }

    @Override
    public boolean isJavaLangString() {
        return false;
    }

    @Override
    public boolean isInt() {
        return false;
    }

    @Override
    public boolean isJavaLangObject() {
        return false;
    }

    @Override
    public boolean isFunctionalInterface() {
        return false;
    }

    @Override
    public boolean isBoxedExcludingVoid() {
        return false;
    }

    @Override
    public boolean isWILDCARD_PARAMETERIZED_TYPE() {
        return false;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public boolean isUnboundTypeParameter() {
        return false;
    }

    @Override
    public boolean isAssignableFrom(Runtime runtime, ParameterizedType other) {
        return false;
    }

    @Override
    public String detailedString() {
        return "";
    }

    @Override
    public String toString() {
        return (typeParameter != null ? "Type " : isTypeParameter() ? "Type param " : "") + detailedString();
    }

}
