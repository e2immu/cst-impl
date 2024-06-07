package org.e2immu.cstimpl.type;

import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.type.TypeParameter;
import org.e2immu.support.Either;

import java.util.List;
import java.util.Set;

public class TypeParameterImpl implements TypeParameter {
    private final int index;
    private final String name;
    private final Either<TypeInfo, MethodInfo> owner;
    private final List<ParameterizedType> typeBounds;

    public TypeParameterImpl(int index, String name, Either<TypeInfo, MethodInfo> owner, List<ParameterizedType> typeBounds) {
        this.index = index;
        this.name = name;
        this.owner = owner;
        this.typeBounds = typeBounds;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Either<TypeInfo, MethodInfo> getOwner() {
        return owner;
    }

    @Override
    public List<ParameterizedType> typeBounds() {
        return typeBounds;
    }

    @Override
    public ParameterizedType toParameterizedType() {
        return null;
    }

    @Override
    public OutputBuilder print(Qualification qualification, Set<TypeParameter> visitedTypeParameters) {
        return null;
    }

    @Override
    public String simpleName() {
        return name;
    }
}
