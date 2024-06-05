package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.info.FieldInfo;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.type.TypeParameter;
import org.e2immu.cstimpl.type.ParameterizedTypeImpl;
import org.e2immu.support.Either;

import java.util.List;

public class TypeInfoImpl implements TypeInfo {
    private final String fullyQualifiedName;
    private final String simpleName;
    private final Either<String, TypeInfo> packageNameOrEnclosingType;

    public TypeInfoImpl(String packageName, String simpleName) {
        fullyQualifiedName = packageName + "." + simpleName;
        this.simpleName = simpleName;
        packageNameOrEnclosingType = Either.left(packageName);
    }

    @Override
    public String fullyQualifiedName() {
        return fullyQualifiedName;
    }

    @Override
    public ParameterizedType asSimpleParameterizedType() {
        return new ParameterizedTypeImpl(this);
    }

    @Override
    public MethodInfo findUniqueMethod(String methodName, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MethodInfo findConstructor(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FieldInfo getFieldByName(String name, boolean complain) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MethodInfo findUniqueMethod(String tryCatch, TypeInfo typeInfoOfFirstParameter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean doesNotRequirePackage() {
        return true;
    }

    @Override
    public String fromPrimaryTypeDownwards() {
        if (packageNameOrEnclosingType.isLeft()) {
            return simpleName;
        }
        return packageNameOrEnclosingType.getRight().fromPrimaryTypeDownwards() + "." + simpleName;
    }

    @Override
    public Either<String, TypeInfo> packageNameOrEnclosingType() {
        return packageNameOrEnclosingType;
    }

    @Override
    public List<TypeParameter> typeParameters() {
        return List.of();
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public String simpleName() {
        return simpleName;
    }

    @Override
    public TypeInfo primaryType() {
        return packageNameOrEnclosingType.isLeft() ? this : packageNameOrEnclosingType.getRight().primaryType();
    }
}
