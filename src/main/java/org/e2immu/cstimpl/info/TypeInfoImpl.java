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
    public static final String JAVA_LANG_OBJECT = "java.lang.Object";

    private final String fullyQualifiedName;
    private final String simpleName;
    private final Either<String, TypeInfo> packageNameOrEnclosingType;

    public TypeInfoImpl(String packageName, String simpleName) {
        fullyQualifiedName = packageName + "." + simpleName;
        this.simpleName = simpleName;
        packageNameOrEnclosingType = Either.left(packageName);
    }

    @Override
    public String packageName() {
        if (packageNameOrEnclosingType.isLeft()) return packageNameOrEnclosingType.getLeft();
        return packageNameOrEnclosingType.getRight().packageName();
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

    public boolean isNumeric() {
        return isInt() || isInteger() ||
               isLong() || isBoxedLong() ||
               isShort() || isBoxedShort() ||
               isByte() || isBoxedByte() ||
               isFloat() || isBoxedFloat() ||
               isDouble() || isBoxedDouble();
    }

    public boolean isBoxedExcludingVoid() {
        return isBoxedByte() || isBoxedShort() || isInteger() || isBoxedLong()
               || isCharacter() || isBoxedFloat() || isBoxedDouble() || isBoxedBoolean();
    }

    public boolean allowInImport() {
        return isNotJavaLang() && !isPrimitiveExcludingVoid() && !isVoid();
    }

    public boolean packageIsExactlyJavaLang() {
        return "java.lang".equals(packageName());
    }

    public boolean isNotJavaLang() {
        return !this.fullyQualifiedName.startsWith("java.lang.");
    }

    public boolean needsParent() {
        return fullyQualifiedName.indexOf('.') > 0 && !fullyQualifiedName.startsWith("java.lang");
    }

    public boolean isJavaLangObject() {
        return JAVA_LANG_OBJECT.equals(this.fullyQualifiedName);
    }

    boolean isJavaLangString() {
        return "java.lang.String".equals(this.fullyQualifiedName);
    }

    boolean isJavaLangClass() {
        return "java.lang.Class".equals(this.fullyQualifiedName);
    }

    boolean isJavaLangVoid() {
        return "java.lang.Void".equals(this.fullyQualifiedName);
    }

    public boolean isJavaIoSerializable() {
        return "java.io.Serializable".equals(fullyQualifiedName);
    }

    public boolean isVoid() {
        return "void".equals(this.fullyQualifiedName);
    }

    public boolean isBoxedFloat() {
        return "java.lang.Float".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isFloat() {
        return "float".equals(this.fullyQualifiedName);
    }

    public boolean isBoxedDouble() {
        return "java.lang.Double".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isDouble() {
        return "double".equals(this.fullyQualifiedName);
    }

    public boolean isBoxedByte() {
        return "java.lang.Byte".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isByte() {
        return "byte".equals(this.fullyQualifiedName);
    }

    public boolean isBoxedShort() {
        return "java.lang.Short".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isShort() {
        return "short".equals(this.fullyQualifiedName);
    }

    public boolean isBoxedLong() {
        return "java.lang.Long".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isLong() {
        return "long".equals(this.fullyQualifiedName);
    }

    public boolean isBoxedBoolean() {
        return "java.lang.Boolean".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isChar() {
        return "char".equals(this.fullyQualifiedName);
    }

    public boolean isInteger() {
        return "java.lang.Integer".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isInt() {
        return "int".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isBoolean() {
        return "boolean".equals(this.fullyQualifiedName);
    }

    public boolean isCharacter() {
        return "java.lang.Character".equals(this.fullyQualifiedName);
    }

    public boolean isPrimitiveExcludingVoid() {
        return this.isByte() || this.isShort() || this.isInt() || this.isLong() ||
               this.isChar() || this.isFloat() || this.isDouble() || this.isBoolean();
    }
}
