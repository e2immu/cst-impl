package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.element.*;
import org.e2immu.cstapi.info.Access;
import org.e2immu.cstapi.info.FieldInfo;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstapi.runtime.Runtime;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.type.TypeNature;
import org.e2immu.cstapi.type.TypeParameter;
import org.e2immu.cstapi.variable.DescendMode;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.type.ParameterizedTypeImpl;
import org.e2immu.support.Either;
import org.e2immu.support.EventuallyFinal;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeInfoImpl extends InfoImpl implements TypeInfo {
    public static final String JAVA_LANG_OBJECT = "java.lang.Object";

    private final String fullyQualifiedName;
    private final String simpleName;
    private final Either<CompilationUnit, TypeInfo> compilationUnitOrEnclosingType;
    private final EventuallyFinal<TypeInspection> inspection = new EventuallyFinal<>();

    public TypeInfoImpl(CompilationUnit compilationUnit, String simpleName) {
        fullyQualifiedName = compilationUnit.packageName() + "." + simpleName;
        this.simpleName = simpleName;
        compilationUnitOrEnclosingType = Either.left(compilationUnit);
        inspection.setVariable(new TypeInspectionImpl.Builder(this));
    }

    public TypeInfoImpl(TypeInfo enclosing, String simpleName) {
        fullyQualifiedName = enclosing.fullyQualifiedName() + "." + simpleName;
        this.simpleName = simpleName;
        compilationUnitOrEnclosingType = Either.right(enclosing);
        inspection.setVariable(new TypeInspectionImpl.Builder(this));
    }

    @Override
    public String packageName() {
        if (compilationUnitOrEnclosingType.isLeft()) return compilationUnitOrEnclosingType.getLeft().packageName();
        return compilationUnitOrEnclosingType.getRight().packageName();
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
    public ParameterizedType parentClass() {
        return inspection.get().parentClass();
    }

    @Override
    public List<ParameterizedType> interfacesImplemented() {
        return inspection.get().interfacesImplemented();
    }

    @Override
    public Stream<MethodInfo> methodStream(Methods methods) {
        return inspection.get().methodStream(methods);
    }

    @Override
    public List<MethodInfo> constructors() {
        return inspection.get().constructors();
    }

    @Override
    public List<FieldInfo> fields() {
        return List.of();
    }

    @Override
    public boolean doesNotRequirePackage() {
        return true;
    }

    @Override
    public String fromPrimaryTypeDownwards() {
        if (compilationUnitOrEnclosingType.isLeft()) {
            return simpleName;
        }
        return compilationUnitOrEnclosingType.getRight().fromPrimaryTypeDownwards() + "." + simpleName;
    }

    @Override
    public Either<CompilationUnit, TypeInfo> compilationUnitOrEnclosingType() {
        return compilationUnitOrEnclosingType;
    }

    @Override
    public Set<TypeInfo> superTypesExcludingJavaLangObject() {
        return Set.of(); // FIXME
    }

    @Override
    public ParameterizedType asParameterizedType(Runtime runtime) {
        List<ParameterizedType> typeParameters = typeParameters()
                .stream().map(TypeParameter::toParameterizedType)
                .collect(Collectors.toList());
        return new ParameterizedTypeImpl(this, typeParameters);
    }

    @Override
    public List<TypeParameter> typeParameters() {
        return List.of(); // FIXME
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public boolean isInterface() {
        return false;
    }

    @Override
    public String simpleName() {
        return simpleName;
    }

    @Override
    public TypeInfo primaryType() {
        return compilationUnitOrEnclosingType.isLeft() ? this : compilationUnitOrEnclosingType.getRight().primaryType();
    }

    @Override
    public boolean isNumeric() {
        return isInt() || isInteger() ||
               isLong() || isBoxedLong() ||
               isShort() || isBoxedShort() ||
               isByte() || isBoxedByte() ||
               isFloat() || isBoxedFloat() ||
               isDouble() || isBoxedDouble();
    }

    @Override
    public boolean isBoxedExcludingVoid() {
        return isBoxedByte() || isBoxedShort() || isInteger() || isBoxedLong()
               || isCharacter() || isBoxedFloat() || isBoxedDouble() || isBoxedBoolean();
    }

    @Override
    public boolean isFunctionalInterface() {
        return inspection.get().singleAbstractMethod() != null;
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

    @Override
    public boolean isJavaLangObject() {
        return JAVA_LANG_OBJECT.equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isJavaLangString() {
        return "java.lang.String".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isJavaLangClass() {
        return "java.lang.Class".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isJavaLangVoid() {
        return "java.lang.Void".equals(this.fullyQualifiedName);
    }

    @Override
    public boolean isJavaIoSerializable() {
        return "java.io.Serializable".equals(fullyQualifiedName);
    }

    @Override
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

    @Override
    public boolean isBoxedBoolean() {
        return "java.lang.Boolean".equals(this.fullyQualifiedName);
    }

    @Override
    public List<TypeInfo> subTypes() {
        return inspection.get().subTypes();
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

    @Override
    public boolean isPrimitiveExcludingVoid() {
        return this.isByte() || this.isShort() || this.isInt() || this.isLong() ||
               this.isChar() || this.isFloat() || this.isDouble() || this.isBoolean();
    }

    @Override
    public boolean isPublic() {
        return inspection.get().isPublic();
    }

    @Override
    public TypeInfo.Builder builder() {
        assert inspection.isVariable();
        return (TypeInfo.Builder) inspection.get();
    }

    @Override
    public TypeNature typeNature() {
        return inspection.get().typeNature();
    }

    @Override
    public int complexity() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Access access() {
        return inspection.get().access();
    }

    @Override
    public MethodInfo singleAbstractMethod() {
        return inspection.get().singleAbstractMethod();
    }

    public void commit(TypeInspection ti) {
        inspection.setFinal(ti);
    }

    @Override
    public List<Comment> comments() {
        return inspection.get().comments();
    }

    @Override
    public Source source() {
        return inspection.get().source();
    }

}
