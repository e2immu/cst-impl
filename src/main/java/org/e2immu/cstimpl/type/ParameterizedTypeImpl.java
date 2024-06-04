package org.e2immu.cstimpl.type;

import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.Diamond;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.type.TypeParameter;

public class ParameterizedTypeImpl implements ParameterizedType {

    private final TypeParameter typeParameter;
    private final TypeInfo typeInfo;
    private final int arrays;

    public ParameterizedTypeImpl(TypeParameter typeParameter, int arrays) {
        this(null, typeParameter, arrays);
    }

    public ParameterizedTypeImpl(TypeInfo typeInfo) {
        this(typeInfo, null, 0);
    }

    public ParameterizedTypeImpl(TypeInfo typeInfo, int arrays) {
        this(typeInfo, null, arrays);
    }

    public ParameterizedTypeImpl(TypeInfo typeInfo, TypeParameter typeParameter, int arrays) {
        this.typeParameter = typeParameter;
        this.typeInfo = typeInfo;
        this.arrays = arrays;
    }

    public OutputBuilder print(Qualification qualification) {
        return ParameterizedTypePrinter.print(qualification, this,
                false, DiamondImpl.SHOW_ALL, false);
    }

    public OutputBuilder print(Qualification qualification, boolean varArgs, Diamond diamond) {
        return ParameterizedTypePrinter.print(qualification, this, varArgs, diamond, false);
    }

    @Override
    public String toString() {
        return (typeParameter != null ? "Type " : isTypeParameter() ? "Type param " : "") + detailedString();
    }

}
