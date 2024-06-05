package org.e2immu.cstimpl.runtime;

import org.e2immu.cstapi.expression.AnnotationExpression;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.runtime.Predefined;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstimpl.element.AnnotationExpressionImpl;
import org.e2immu.cstimpl.info.*;

import java.util.*;

public class PredefinedImpl implements Predefined {
    private static final String JAVA_PRIMITIVE = "";
    private static final String JAVA_LANG = "";

    public static final String ANNOTATION_TYPE = "annotation type";
    public final TypeInfo intTypeInfo = new TypeInfoImpl(JAVA_PRIMITIVE, "int");
    public final ParameterizedType intParameterizedType = intTypeInfo.asSimpleParameterizedType();

    public final TypeInfo integerTypeInfo = new TypeInfoImpl(JAVA_LANG, "Integer");

    public final TypeInfo charTypeInfo = new TypeInfoImpl(JAVA_PRIMITIVE, "char");
    public final ParameterizedType charParameterizedType = charTypeInfo.asSimpleParameterizedType();

    public final TypeInfo characterTypeInfo = new TypeInfoImpl(JAVA_LANG, "Character");

    public final TypeInfo booleanTypeInfo = new TypeInfoImpl(JAVA_PRIMITIVE, "boolean");
    public final ParameterizedType booleanParameterizedType = booleanTypeInfo.asSimpleParameterizedType();

    public final TypeInfo boxedBooleanTypeInfo = new TypeInfoImpl(JAVA_LANG, "Boolean");
    public final ParameterizedType boxedBooleanParameterizedType = boxedBooleanTypeInfo.asSimpleParameterizedType();

    public final TypeInfo longTypeInfo = new TypeInfoImpl(JAVA_PRIMITIVE, "long");
    public final ParameterizedType longParameterizedType = longTypeInfo.asSimpleParameterizedType();

    public final TypeInfo boxedLongTypeInfo = new TypeInfoImpl(JAVA_LANG, "Long");

    public final TypeInfo shortTypeInfo = new TypeInfoImpl(JAVA_PRIMITIVE, "short");
    public final ParameterizedType shortParameterizedType = shortTypeInfo.asSimpleParameterizedType();

    public final TypeInfo boxedShortTypeInfo = new TypeInfoImpl(JAVA_LANG, "Short");

    public final TypeInfo byteTypeInfo = new TypeInfoImpl(JAVA_PRIMITIVE, "byte");
    public final ParameterizedType byteParameterizedType = byteTypeInfo.asSimpleParameterizedType();

    public final TypeInfo boxedByteTypeInfo = new TypeInfoImpl(JAVA_LANG, "Byte");

    public final TypeInfo doubleTypeInfo = new TypeInfoImpl(JAVA_PRIMITIVE, "double");
    public final ParameterizedType doubleParameterizedType = doubleTypeInfo.asSimpleParameterizedType();

    public final TypeInfo boxedDoubleTypeInfo = new TypeInfoImpl(JAVA_LANG, "Double");

    public final TypeInfo floatTypeInfo = new TypeInfoImpl(JAVA_PRIMITIVE, "float");
    public final ParameterizedType floatParameterizedType = floatTypeInfo.asSimpleParameterizedType();

    public final TypeInfo boxedFloatTypeInfo = new TypeInfoImpl(JAVA_LANG, "Float");

    public final TypeInfo voidTypeInfo = new TypeInfoImpl(JAVA_PRIMITIVE, "void");
    public final ParameterizedType voidParameterizedType = voidTypeInfo.asSimpleParameterizedType();
    public final TypeInfo boxedVoidTypeInfo = new TypeInfoImpl(JAVA_LANG, "Void");

    public final TypeInfo stringTypeInfo = new TypeInfoImpl(JAVA_LANG, "String");
    public final ParameterizedType stringParameterizedType = stringTypeInfo.asSimpleParameterizedType();

    public ParameterizedType stringParameterizedType() {
        return stringParameterizedType;
    }

    @Override
    public ParameterizedType intParameterizedType() {
        return intParameterizedType;
    }

    @Override
    public ParameterizedType booleanParameterizedType() {
        return booleanParameterizedType;
    }

    @Override
    public ParameterizedType boxedBooleanParameterizedType() {
        return boxedBooleanParameterizedType;
    }

    @Override
    public ParameterizedType longParameterizedType() {
        return longParameterizedType;
    }

    @Override
    public ParameterizedType doubleParameterizedType() {
        return doubleParameterizedType;
    }

    @Override
    public ParameterizedType floatParameterizedType() {
        return floatParameterizedType;
    }

    @Override
    public ParameterizedType shortParameterizedType() {
        return shortParameterizedType;
    }

    @Override
    public ParameterizedType charParameterizedType() {
        return charParameterizedType;
    }

    public MethodInfo createOperator(TypeInfo owner,
                                     String name,
                                     List<ParameterizedType> parameterizedTypes,
                                     ParameterizedType returnType) {
        MethodInfoImpl mi = new MethodInfoImpl(MethodInfoImpl.MethodType.STATIC_METHOD, name, owner);
        int i = 0;
        for (ParameterizedType parameterizedType : parameterizedTypes) {
            ParameterInfoImpl pi = new ParameterInfoImpl(mi, i, "p" + i, parameterizedType);
            pi.builder().setVarArgs(true);
            pi.endOfInspection();
            mi.builder().addParameter(pi); // inspection built when method is built
        }
        mi.builder().setReturnType(returnType);
        mi.builder().setAccess(Inspection.Access.PUBLIC);
        mi.endOfInspection();
        return mi;
    }

    public final TypeInfo functionalInterface = new TypeInfoImpl(JAVA_LANG, "FunctionalInterface");
    public final AnnotationExpression functionalInterfaceAnnotationExpression =
            new AnnotationExpressionImpl(functionalInterface, List.of());

    public final TypeInfo classTypeInfo = new TypeInfoImpl(JAVA_LANG, "Class");

    public final TypeInfo objectTypeInfo = new TypeInfoImpl(JAVA_LANG, "Object");
    public final ParameterizedType objectParameterizedType = objectTypeInfo.asSimpleParameterizedType();

    private final List<ParameterizedType> intInt = List.of(intParameterizedType, intParameterizedType);
    private final List<ParameterizedType> boolBool = List.of(booleanParameterizedType, booleanParameterizedType);

    public final MethodInfo plusOperatorInt = createOperator(intTypeInfo, "+", intInt, intParameterizedType);
    public final MethodInfo minusOperatorInt = createOperator(intTypeInfo, "-", intInt, intParameterizedType);
    public final MethodInfo bitwiseOrOperatorInt = createOperator(intTypeInfo, "|", intInt, intParameterizedType);
    public final MethodInfo bitwiseAndOperatorInt = createOperator(intTypeInfo, "&", intInt, intParameterizedType);
    public final MethodInfo bitwiseXorOperatorInt = createOperator(intTypeInfo, "^", intInt, intParameterizedType);
    public final MethodInfo remainderOperatorInt = createOperator(intTypeInfo, "%", intInt, intParameterizedType);
    public final MethodInfo signedRightShiftOperatorInt = createOperator(intTypeInfo, ">>", intInt, intParameterizedType);
    public final MethodInfo unsignedRightShiftOperatorInt = createOperator(intTypeInfo, ">>>", intInt, intParameterizedType);
    public final MethodInfo leftShiftOperatorInt = createOperator(intTypeInfo, "<<", intInt, intParameterizedType);
    public final MethodInfo divideOperatorInt = createOperator(intTypeInfo, "/", intInt, intParameterizedType);
    public final MethodInfo multiplyOperatorInt = createOperator(intTypeInfo, "*", intInt, intParameterizedType);

    public final MethodInfo equalsOperatorInt = createOperator(intTypeInfo, "==", intInt, booleanParameterizedType);
    public final MethodInfo notEqualsOperatorInt = createOperator(intTypeInfo, "!=", intInt, booleanParameterizedType);
    public final MethodInfo greaterOperatorInt = createOperator(intTypeInfo, ">", intInt, booleanParameterizedType);
    public final MethodInfo greaterEqualsOperatorInt = createOperator(intTypeInfo, ">=", intInt, booleanParameterizedType);
    public final MethodInfo lessOperatorInt = createOperator(intTypeInfo, "<", intInt, booleanParameterizedType);
    public final MethodInfo lessEqualsOperatorInt = createOperator(intTypeInfo, "<=", intInt, booleanParameterizedType);

    public final MethodInfo assignOperatorInt = createOperator(intTypeInfo, "=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignPlusOperatorInt = createOperator(intTypeInfo, "+=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignMinusOperatorInt = createOperator(intTypeInfo, "-=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignMultiplyOperatorInt = createOperator(intTypeInfo, "*=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignDivideOperatorInt = createOperator(intTypeInfo, "/=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignOrOperatorInt = createOperator(intTypeInfo, "|=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignAndOperatorInt = createOperator(intTypeInfo, "&=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignXorOperatorInt = createOperator(intTypeInfo, "^=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignLeftShiftOperator = createOperator(intTypeInfo, "<<=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignSignedRightShiftOperator = createOperator(intTypeInfo, ">>=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignUnsignedRightShiftOperator = createOperator(intTypeInfo, ">>>=", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo assignRemainderOperatorInt = createOperator(intTypeInfo, "%=", List.of(intParameterizedType), intParameterizedType);

    // TODO long instead of int to distinguish statically (isPostfix) This is a hack!
    public final MethodInfo postfixIncrementOperatorInt = createOperator(intTypeInfo, "++", List.of(), longParameterizedType);
    public final MethodInfo prefixIncrementOperatorInt = createOperator(intTypeInfo, "++", List.of(), intParameterizedType);
    public final MethodInfo postfixDecrementOperatorInt = createOperator(intTypeInfo, "--", List.of(), longParameterizedType);
    public final MethodInfo prefixDecrementOperatorInt = createOperator(intTypeInfo, "--", List.of(), intParameterizedType);

    public final MethodInfo unaryPlusOperatorInt = createOperator(intTypeInfo, "+", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo unaryMinusOperatorInt = createOperator(intTypeInfo, "-", List.of(intParameterizedType), intParameterizedType);

    public final MethodInfo bitWiseNotOperatorInt = createOperator(intTypeInfo, "~", List.of(intParameterizedType), intParameterizedType);
    public final MethodInfo logicalNotOperatorBool = createOperator(booleanTypeInfo, "!", List.of(booleanParameterizedType), booleanParameterizedType);
    public final MethodInfo orOperatorBool = createOperator(booleanTypeInfo, "||", boolBool, booleanParameterizedType);
    public final MethodInfo andOperatorBool = createOperator(booleanTypeInfo, "&&", boolBool, booleanParameterizedType);
    public final MethodInfo xorOperatorBool = createOperator(booleanTypeInfo, "^", boolBool, booleanParameterizedType);

    public final MethodInfo plusOperatorString = createOperator(stringTypeInfo, "+", List.of(stringParameterizedType,
            stringParameterizedType), stringParameterizedType);

    public final MethodInfo equalsOperatorObject = createOperator(objectTypeInfo, "==",
            List.of(objectParameterizedType, objectParameterizedType), booleanParameterizedType);
    public final MethodInfo notEqualsOperatorObject = createOperator(objectTypeInfo, "!=",
            List.of(objectParameterizedType, objectParameterizedType), booleanParameterizedType);

    public final Map<String, TypeInfo> primitiveByName = new HashMap<>();
    public final Map<String, TypeInfo> typeByName = new HashMap<>();

    public Map<String, TypeInfo> getTypeByName() {
        return typeByName;
    }

    public Map<String, TypeInfo> getPrimitiveByName() {
        return primitiveByName;
    }

    public final Set<TypeInfo> boxed = Set.of(boxedBooleanTypeInfo, boxedByteTypeInfo, boxedDoubleTypeInfo, boxedFloatTypeInfo,
            boxedLongTypeInfo, boxedShortTypeInfo, boxedVoidTypeInfo, integerTypeInfo, characterTypeInfo);

    public final Set<TypeInfo> primitives = Set.of(booleanTypeInfo, byteTypeInfo, doubleTypeInfo, floatTypeInfo,
            longTypeInfo, shortTypeInfo, voidTypeInfo, intTypeInfo, charTypeInfo);


    public PredefinedImpl() {
        for (TypeInfo ti : primitives) {
            primitiveByName.put(ti.simpleName(), ti);
        }
        for (TypeInfo ti : List.of(stringTypeInfo, objectTypeInfo, classTypeInfo, functionalInterface)) {
            typeByName.put(ti.simpleName(), ti);
        }
        for (TypeInfo ti : boxed) {
            typeByName.put(ti.simpleName(), ti);
        }
    }

    @Override
    public ParameterizedType widestType(ParameterizedType t1, ParameterizedType t2) {
        int o1 = primitiveTypeOrder(Objects.requireNonNull(t1));
        int o2 = primitiveTypeOrder(Objects.requireNonNull(t2));
        if (o1 >= o2) return t1;
        return t2;
    }


    /*
    used by binaryOperator, the result cannot be null, so must be the unboxed version (this contrasts with
    ParameterizedType.commonType)
     */
    @Override
    public ParameterizedType widestTypeUnbox(ParameterizedType t1, ParameterizedType t2) {
        ParameterizedType u1, u2;
        if (t1.isBoxedExcludingVoid()) {
            u1 = unboxed(t1.typeInfo()).asSimpleParameterizedType();
        } else {
            u1 = t1;
        }
        if (t2.isBoxedExcludingVoid()) {
            u2 = unboxed(t2.typeInfo()).asSimpleParameterizedType();
        } else {
            u2 = t2;
        }
        int o1 = primitiveTypeOrder(u1);
        int o2 = primitiveTypeOrder(u2);
        if (o1 >= o2) return u1;
        return u2;
    }

    @Override
    public int primitiveTypeOrder(ParameterizedType pt) {
        if (pt == null) throw new NullPointerException();
        if (pt.isType()) {
            TypeInfo typeInfo = pt.typeInfo();
            if (typeInfo == booleanTypeInfo) return 1;
            if (typeInfo == byteTypeInfo) return 2;
            if (typeInfo == charTypeInfo) return 3;
            if (typeInfo == shortTypeInfo) return 4;
            if (typeInfo == intTypeInfo) return 5;
            if (typeInfo == floatTypeInfo) return 6;
            if (typeInfo == longTypeInfo) return 7;
            if (typeInfo == doubleTypeInfo) return 8;
            if (typeInfo == stringTypeInfo) return 9;
        }
        return 0;
    }

    @Override
    public int reversePrimitiveTypeOrder(ParameterizedType pt) {
        return 9 - primitiveTypeOrder(pt);
    }

    @Override
    public TypeInfo primitiveByName(String asString) {
        TypeInfo ti = primitiveByName.get(asString);
        if (ti == null) throw new UnsupportedOperationException("Type " + asString + " not (yet) a primitive");
        return ti;
    }

    @Override
    public int isAssignableFromTo(ParameterizedType from, ParameterizedType to, boolean covariant) {
        int fromOrder = primitiveTypeOrder(from);
        if (fromOrder <= 1 || fromOrder >= 9) return -1;
        int toOrder = primitiveTypeOrder(to);
        if (toOrder <= 1 || toOrder >= 9) return -1;
        int diff = covariant ? toOrder - fromOrder : fromOrder - toOrder;
        return diff < 0 ? -1 : diff;
    }

    @Override
    public boolean isPreOrPostFixOperator(MethodInfo operator) {
        return operator == postfixDecrementOperatorInt || // i--;
               operator == postfixIncrementOperatorInt || // i++;
               operator == prefixDecrementOperatorInt || // --i;
               operator == prefixIncrementOperatorInt; // ++i;
    }

    @Override
    public boolean isPrefixOperator(MethodInfo operator) {
        return operator == prefixDecrementOperatorInt || operator == prefixIncrementOperatorInt;
    }

    @Override
    public MethodInfo prePostFixToAssignment(MethodInfo operator) {
        if (operator == postfixDecrementOperatorInt || operator == prefixDecrementOperatorInt) {
            return assignMinusOperatorInt;
        }
        if (operator == postfixIncrementOperatorInt || operator == prefixIncrementOperatorInt) {
            return assignPlusOperatorInt;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public TypeInfo boxed(TypeInfo typeInfo) {
        if (typeInfo == longTypeInfo)
            return boxedLongTypeInfo;
        if (typeInfo == intTypeInfo)
            return integerTypeInfo;
        if (typeInfo == shortTypeInfo)
            return boxedShortTypeInfo;
        if (typeInfo == byteTypeInfo)
            return boxedByteTypeInfo;
        if (typeInfo == charTypeInfo)
            return characterTypeInfo;
        if (typeInfo == booleanTypeInfo)
            return boxedBooleanTypeInfo;
        if (typeInfo == floatTypeInfo)
            return boxedFloatTypeInfo;
        if (typeInfo == doubleTypeInfo)
            return boxedDoubleTypeInfo;
        if (typeInfo == voidTypeInfo) {
            return boxedVoidTypeInfo;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public TypeInfo unboxed(TypeInfo typeInfo) {
        if (typeInfo == boxedLongTypeInfo)
            return longTypeInfo;
        if (typeInfo == integerTypeInfo)
            return intTypeInfo;
        if (typeInfo == boxedShortTypeInfo)
            return shortTypeInfo;
        if (typeInfo == boxedByteTypeInfo)
            return byteTypeInfo;
        if (typeInfo == characterTypeInfo)
            return charTypeInfo;
        if (typeInfo == boxedBooleanTypeInfo)
            return booleanTypeInfo;
        if (typeInfo == boxedFloatTypeInfo)
            return floatTypeInfo;
        if (typeInfo == boxedDoubleTypeInfo)
            return doubleTypeInfo;
        if (typeInfo == boxedVoidTypeInfo) {
            return voidTypeInfo;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public MethodInfo assignOperatorInt() {
        return assignOperatorInt;
    }

    @Override
    public MethodInfo assignPlusOperatorInt() {
        return assignPlusOperatorInt;
    }

    @Override
    public MethodInfo assignMinusOperatorInt() {
        return assignMinusOperatorInt;
    }

    @Override
    public MethodInfo assignMultiplyOperatorInt() {
        return assignMultiplyOperatorInt;
    }

    @Override
    public MethodInfo assignDivideOperatorInt() {
        return assignDivideOperatorInt;
    }

    @Override
    public MethodInfo assignOrOperatorInt() {
        return assignOrOperatorInt;
    }

    @Override
    public MethodInfo assignAndOperatorInt() {
        return assignAndOperatorInt;
    }

    @Override
    public MethodInfo assignLeftShiftOperatorInt() {
        return assignLeftShiftOperator;
    }

    @Override
    public MethodInfo assignSignedRightShiftOperatorInt() {
        return assignSignedRightShiftOperator;
    }

    @Override
    public MethodInfo assignUnsignedRightShiftOperatorInt() {
        return assignUnsignedRightShiftOperator;
    }

    @Override
    public MethodInfo assignXorOperatorInt() {
        return assignXorOperatorInt;
    }

    @Override
    public MethodInfo assignRemainderOperatorInt() {
        return assignRemainderOperatorInt;
    }

    @Override
    public MethodInfo plusOperatorInt() {
        return plusOperatorInt;
    }

    @Override
    public MethodInfo minusOperatorInt() {
        return minusOperatorInt;
    }

    @Override
    public MethodInfo multiplyOperatorInt() {
        return multiplyOperatorInt;
    }

    @Override
    public MethodInfo divideOperatorInt() {
        return divideOperatorInt;
    }

    @Override
    public MethodInfo orOperatorInt() {
        return bitwiseOrOperatorInt;
    }

    @Override
    public MethodInfo andOperatorInt() {
        return bitwiseAndOperatorInt;
    }

    @Override
    public MethodInfo xorOperatorInt() {
        return bitwiseXorOperatorInt;
    }

    @Override
    public MethodInfo orOperatorBool() {
        return orOperatorBool;
    }

    @Override
    public MethodInfo andOperatorBool() {
        return andOperatorBool;
    }

    @Override
    public MethodInfo equalsOperatorObject() {
        return equalsOperatorObject;
    }

    @Override
    public MethodInfo equalsOperatorInt() {
        return equalsOperatorInt;
    }

    @Override
    public MethodInfo notEqualsOperatorObject() {
        return notEqualsOperatorObject;
    }

    @Override
    public MethodInfo notEqualsOperatorInt() {
        return notEqualsOperatorInt;
    }

    @Override
    public MethodInfo plusOperatorString() {
        return plusOperatorString;
    }

    @Override
    public MethodInfo xorOperatorBool() {
        return xorOperatorBool;
    }

    @Override
    public MethodInfo bitwiseXorOperatorInt() {
        return bitwiseXorOperatorInt;
    }

    @Override
    public MethodInfo leftShiftOperatorInt() {
        return leftShiftOperatorInt;
    }

    @Override
    public MethodInfo signedRightShiftOperatorInt() {
        return signedRightShiftOperatorInt;
    }

    @Override
    public MethodInfo unsignedRightShiftOperatorInt() {
        return unsignedRightShiftOperatorInt;
    }

    @Override
    public MethodInfo greaterOperatorInt() {
        return greaterOperatorInt;
    }

    @Override
    public MethodInfo greaterEqualsOperatorInt() {
        return greaterEqualsOperatorInt;
    }

    @Override
    public MethodInfo lessEqualsOperatorInt() {
        return lessEqualsOperatorInt;
    }

    @Override
    public MethodInfo lessOperatorInt() {
        return lessOperatorInt;
    }

    @Override
    public MethodInfo remainderOperatorInt() {
        return remainderOperatorInt;
    }

    @Override
    public TypeInfo stringTypeInfo() {
        return stringTypeInfo;
    }

    @Override
    public TypeInfo booleanTypeInfo() {
        return booleanTypeInfo;
    }

    @Override
    public TypeInfo charTypeInfo() {
        return charTypeInfo;
    }

    @Override
    public ParameterizedType byteParameterizedType() {
        return byteParameterizedType;
    }

    @Override
    public TypeInfo classTypeInfo() {
        return classTypeInfo;
    }

    @Override
    public ParameterizedType objectParameterizedType() {
        return objectParameterizedType;
    }

    @Override
    public ParameterizedType voidParameterizedType() {
        return voidParameterizedType;
    }

    @Override
    public MethodInfo logicalNotOperatorBool() {
        return logicalNotOperatorBool;
    }

    @Override
    public MethodInfo unaryMinusOperatorInt() {
        return unaryMinusOperatorInt;
    }

    @Override
    public MethodInfo unaryPlusOperatorInt() {
        return unaryPlusOperatorInt;
    }

    @Override
    public MethodInfo prefixIncrementOperatorInt() {
        return prefixIncrementOperatorInt;
    }

    @Override
    public MethodInfo postfixIncrementOperatorInt() {
        return postfixIncrementOperatorInt;
    }

    @Override
    public MethodInfo prefixDecrementOperatorInt() {
        return prefixDecrementOperatorInt;
    }

    @Override
    public MethodInfo postfixDecrementOperatorInt() {
        return postfixDecrementOperatorInt;
    }

    @Override
    public MethodInfo bitWiseNotOperatorInt() {
        return bitWiseNotOperatorInt;
    }

    @Override
    public TypeInfo integerTypeInfo() {
        return integerTypeInfo;
    }

    @Override
    public TypeInfo intTypeInfo() {
        return intTypeInfo;
    }

    @Override
    public TypeInfo boxedBooleanTypeInfo() {
        return boxedBooleanTypeInfo;
    }

    @Override
    public TypeInfo characterTypeInfo() {
        return characterTypeInfo;
    }

    @Override
    public TypeInfo objectTypeInfo() {
        return objectTypeInfo;
    }

    @Override
    public AnnotationExpression functionalInterfaceAnnotationExpression() {
        return functionalInterfaceAnnotationExpression;
    }

    @Override
    public MethodInfo assignOperator(ParameterizedType returnType) {
        // NOTE: we have only one at the moment, no distinction between the types
        return assignOperatorInt;
    }
}
