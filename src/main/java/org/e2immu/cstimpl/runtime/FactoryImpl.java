package org.e2immu.cstimpl.runtime;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.expression.*;
import org.e2immu.cstapi.info.FieldInfo;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.MethodModifier;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.runtime.Factory;
import org.e2immu.cstapi.statement.*;
import org.e2immu.cstapi.translate.TranslationMap;
import org.e2immu.cstapi.type.*;
import org.e2immu.cstapi.variable.*;
import org.e2immu.cstimpl.expression.*;
import org.e2immu.cstimpl.expression.util.PrecedenceEnum;
import org.e2immu.cstimpl.info.MethodInfoImpl;
import org.e2immu.cstimpl.info.MethodModifierEnum;
import org.e2immu.cstimpl.info.TypeNatureEnum;
import org.e2immu.cstimpl.statement.BlockImpl;
import org.e2immu.cstimpl.statement.LocalVariableCreationImpl;
import org.e2immu.cstimpl.statement.ReturnStatementImpl;
import org.e2immu.cstimpl.translate.TranslationMapImpl;
import org.e2immu.cstimpl.type.DiamondEnum;
import org.e2immu.cstimpl.util.IntUtil;
import org.e2immu.cstimpl.variable.FieldReferenceImpl;
import org.e2immu.cstimpl.variable.LocalVariableImpl;
import org.e2immu.cstimpl.variable.ThisImpl;

import java.util.List;

public class FactoryImpl extends PredefinedImpl implements Factory {

    private final IntConstant zero;
    private final IntConstant one;
    private final IntConstant minusOne;

    public FactoryImpl() {
        zero = new IntConstantImpl(this, 0);
        one = new IntConstantImpl(this, 1);
        minusOne = new IntConstantImpl(this, -1);
    }

    @Override
    public TypeNature typeNatureCLASS() {
        return TypeNatureEnum.CLASS;
    }

    @Override
    public Assignment newAssignment(Expression target, Expression value) {
        return null;
    }

    @Override
    public Equals newEquals(Expression lhs, Expression rhs) {
        MethodInfo operator = lhs.isNumeric() && rhs.isNumeric() ? equalsOperatorInt()
                : equalsOperatorObject();
        return new EqualsImpl(operator, PrecedenceEnum.EQUALITY, lhs, rhs);
    }

    @Override
    public InstanceOf newInstanceOf(ParameterizedType parameterizedType, Expression expression, LocalVariable patternVariable) {
        return null;
    }

    @Override
    public BinaryOperator newBinaryOperator(Expression lhs, MethodInfo operator, Expression rhs, Precedence precedence) {
        return new BinaryOperatorImpl(operator, precedence, lhs, rhs);
    }

    @Override
    public MethodCall newMethodCall(boolean b, Expression newObject, MethodInfo methodInfo, ParameterizedType parameterizedType, List<Expression> newParams) {
        return null;
    }

    @Override
    public ConstructorCall newObjectCreation(Expression scope, MethodInfo constructor, ParameterizedType parameterizedType, Diamond diamond, List<Expression> newParams) {
        return null;
    }

    @Override
    public GreaterThanZero newGreaterThanZero(Expression e, boolean allowEquals) {
        return null;
    }

    @Override
    public Cast newCast(Expression e, ParameterizedType parameterizedType) {
        return null;
    }

    @Override
    public MethodReference newMethodReference(Expression e, MethodInfo methodInfo, ParameterizedType parameterizedType) {
        return null;
    }

    @Override
    public UnaryOperator newUnaryOperator(MethodInfo operator, Expression e, Precedence precedence) {
        return new UnaryOperatorImpl(operator, e, precedence);
    }

    @Override
    public ArrayInitializer newArrayInitializer(List<Expression> expressions, ParameterizedType commonType) {
        return new ArrayInitializerImpl(expressions, commonType);
    }

    @Override
    public ArrayLength newArrayLength(Expression scope) {
        return new ArrayLengthImpl(this, scope);
    }

    @Override
    public MethodCall newMethodCall(Expression object, MethodInfo takeWhile, List<Expression> parameterExpressions) {
        return null;
    }

    @Override
    public TypeExpression newTypeExpression(ParameterizedType parameterizedType, Diamond diamond) {
        return new TypeExpressionImpl(parameterizedType, diamond);
    }

    @Override
    public ConstructorCall newConstructorCall(Expression scope, MethodInfo constructor, ParameterizedType pt, Diamond diamond, List<Expression> parameterExpressions, TypeInfo anonymousClass, ArrayInitializer arrayInitializer) {
        return null;
    }

    @Override
    public IfElseStatement newIfElseStatement(Expression condition, Block ifBlock, Block elseBlock) {
        return null;
    }

    @Override
    public ExpressionAsStatement newExpressionAsStatement(Expression standardized) {
        return null;
    }

    @Override
    public ThrowStatement newThrowStatement(Expression expression) {
        return null;
    }

    @Override
    public AssertStatement newAssertStatement(Expression check, Expression message) {
        return null;
    }

    @Override
    public ReturnStatement newReturnStatement(Expression expression) {
        return newReturnStatement(expression);
    }

    @Override
    public Element.Builder newReturnStatementBuilder() {
        return new ReturnStatementImpl.Builder();
    }

    @Override
    public WhileStatement newWhileStatement(Expression loopCondition, Block block) {
        return null;
    }

    @Override
    public Block.Builder newBlockBuilder() {
        return new BlockImpl.Builder();
    }

    @Override
    public Block emptyBlock() {
        return new BlockImpl();
    }

    @Override
    public Assignment newAssignment(Expression target, Expression value, MethodInfo assignmentOperator, Boolean prefixPrimitiveOperator, boolean complainAboutAssignmentOutsideType, boolean allowStaticallyAssigned, Expression evaluationOfValue) {
        return null;
    }

    @Override
    public VariableExpression newVariableExpression(Variable variable) {
        return new VariableExpressionImpl(variable);
    }

    @Override
    public StringConstant newStringConstant(String string) {
        return null;
    }

    @Override
    public ConstructorCall objectCreation(Expression scope, MethodInfo constructor, ParameterizedType parameterizedType, Diamond diamond, List<Expression> parameterExpressions) {
        return null;
    }

    @Override
    public TypeInfo newTypeInfo(TypeInfo typeInfo, String capitalized) {
        return null;
    }

    @Override
    public ParameterizedType newParameterizedType(TypeInfo typeInfo, List<ParameterizedType> newParameters) {
        return null;
    }

    @Override
    public ParameterizedType newParameterizedType(TypeInfo typeInfo, int arrays) {
        return null;
    }

    @Override
    public ParameterizedType newParameterizedType(TypeParameter typeParameter, int index, Wildcard wildCard) {
        return null;
    }

    @Override
    public TypeParameter newTypeParameter(String typeParameterName, int tpCnt) {
        return null;
    }

    @Override
    public ParameterizedType WILDCARD_PARAMETERIZED_TYPE() {
        return null;
    }

    @Override
    public ParameterizedType parameterizedTypeRETURN_TYPE_OF_CONSTRUCTOR() {
        return null;
    }

    @Override
    public Diamond diamondYES() {
        return DiamondEnum.YES;
    }

    @Override
    public Diamond diamondNO() {
        return DiamondEnum.NO;
    }

    @Override
    public Expression constructorCallWithArrayInitializer(MethodInfo constructor, ParameterizedType returnType, List<Object> of, ArrayInitializer initializer) {
        return null;
    }

    @Override
    public MethodModifier methodModifierSTATIC() {
        return MethodModifierEnum.STATIC;
    }

    @Override
    public MethodModifier methodModifierPUBLIC() {
        return MethodModifierEnum.PUBLIC;
    }

    @Override
    public Or newOr(List<Expression> expressions) {
        return new OrImpl(this, expressions);
    }

    @Override
    public InlineConditional newInlineConditional(Expression condition, Expression ifTrue, Expression ifFalse) {
        return new InlineConditionalImpl(condition, ifTrue, ifFalse);
    }

    @Override
    public SwitchExpression newSwitchExpression(VariableExpression selector, List<SwitchEntry> switchEntries, ParameterizedType parameterizedType, List<Expression> expressions) {
        return null;
    }

    @Override
    public SwitchEntry newStatementsSwitchEntry(VariableExpression selector, List<Expression> labels, List<Statement> statements) {
        return null;
    }

    @Override
    public MethodCall newMethodCall(boolean objectIsImplicit, Expression object, MethodInfo methodInfo, ParameterizedType parameterizedType, List<Expression> expressions, String modificationTimes) {
        return null;
    }

    @Override
    public CharConstant newCharConstant(char c) {
        return null;
    }

    @Override
    public This newThis(TypeInfo typeInfo) {
        return new ThisImpl(typeInfo);
    }

    @Override
    public DependentVariable newDependentVariable(Expression array, Expression index, String statementIndex, TypeInfo owningType) {
        return null;
    }

    @Override
    public Expression newMultiExpressions(List<Expression> newExpressions) {
        return null;
    }

    @Override
    public BooleanConstant newBooleanConstant(boolean value) {
        return new BooleanConstantImpl(this, value);
    }

    @Override
    public IntConstant zero() {
        return zero;
    }

    @Override
    public IntConstant one() {
        return one;
    }

    @Override
    public IntConstant minusOne() {
        return minusOne;
    }

    @Override
    public Instance newInstanceForTooComplex(ParameterizedType parameterizedType) {
        return null;
    }

    @Override
    public ParameterizedType commonType(ParameterizedType pt1, ParameterizedType pt2) {
        return null;
    }

    @Override
    public Precedence precedenceUNARY() {
        return PrecedenceEnum.UNARY;
    }

    @Override
    public Precedence precedenceEQUALITY() {
        return PrecedenceEnum.EQUALITY;
    }

    @Override
    public Expression nullValue(TypeInfo typeInfo) {
        if (typeInfo != null) {
            if (typeInfo.isBoolean()) return newBooleanConstant(false);
            if (typeInfo.isInt()) return zero;
            if (typeInfo.isLong()) return newLong(0L);
            if (typeInfo.isShort()) return newShort((short) 0);
            if (typeInfo.isByte()) return newByte((byte) 0);
            if (typeInfo.isFloat()) return newFloat(0);
            if (typeInfo.isDouble()) return newDouble(0);
            if (typeInfo.isChar()) return newCharConstant('\0');
        }
        return nullConstant();
    }

    @Override
    public Precedence precedenceGREATERTHAN() {
        return PrecedenceEnum.EQUALITY;
    }

    @Override
    public Precedence precedenceAND() {
        return PrecedenceEnum.AND;
    }

    @Override
    public Precedence precedenceOR() {
        return PrecedenceEnum.OR;
    }

    @Override
    public Precedence precedenceASSIGNMENT() {
        return PrecedenceEnum.ASSIGNMENT;
    }

    @Override
    public Precedence precedenceMULTIPLICATIVE() {
        return PrecedenceEnum.MULTIPLICATIVE;
    }

    @Override
    public Precedence precedenceADDITIVE() {
        return PrecedenceEnum.ADDITIVE;
    }

    @Override
    public IntConstant newInt(int i) {
        return new IntConstantImpl(this, i);
    }

    @Override
    public LongConstant newLong(long l) {
        return new LongConstantImpl(this, l);
    }

    @Override
    public ShortConstant newShort(short s) {
        return null;
    }

    @Override
    public ByteConstant newByte(byte b) {
        return null;
    }

    @Override
    public FloatConstant newFloat(float f) {
        return null;
    }

    @Override
    public DoubleConstant newDouble(double d) {
        return null;
    }

    @Override
    public Numeric intOrDouble(double v) {
        if (IntUtil.isMathematicalInteger(v)) {
            long l = Math.round(v);
            if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
                return newLong(l);
            }
            return newInt((int) l);
        }
        return newDouble(v);
    }

    @Override
    public Expression nullConstant() {
        return null;
    }

    @Override
    public LocalVariable newLocalVariable(String name, ParameterizedType parameterizedType) {
        return new LocalVariableImpl(name, parameterizedType, null);
    }

    @Override
    public LocalVariable newLocalVariable(String name,
                                          ParameterizedType parameterizedType,
                                          Expression assignmentExpression) {
        return new LocalVariableImpl(name, parameterizedType, assignmentExpression);
    }

    @Override
    public EmptyExpression newEmptyExpression() {
        return new EmptyExpressionImpl(this, EmptyExpressionImpl.EMPTY_EXPRESSION);
    }

    @Override
    public EmptyExpression newEmptyExpression(String msg) {
        return new EmptyExpressionImpl(this, msg);
    }

    @Override
    public LocalVariableCreation newLocalVariableCreation(LocalVariable localVariable) {
        return new LocalVariableCreationImpl(localVariable);
    }

    @Override
    public TranslationMap.Builder newTranslationMapBuilder() {
        return new TranslationMapImpl.Builder();
    }

    @Override
    public TranslationMap.Builder newTranslationMapBuilder(TranslationMap startingPoint) {
        return new TranslationMapImpl.Builder(startingPoint);
    }

    @Override
    public Lambda newLambda(ParameterizedType abstractFunctionalType, ParameterizedType implementation, ParameterizedType concreteReturnType, List<Lambda.OutputVariant> outputVariants) {
        return null;
    }

    @Override
    public FieldReference newFieldReference(FieldInfo fieldInfo, Expression scope) {
        return new FieldReferenceImpl(fieldInfo, scope);
    }

    @Override
    public MethodInfo newMethod(TypeInfo owner) {
        return new MethodInfoImpl(owner);
    }

    @Override
    public MethodInfo newMethod(TypeInfo owner, String name, MethodInfo.MethodType methodType) {
        return new MethodInfoImpl(methodType, name, owner);
    }

    @Override
    public MethodInfo.MethodType newMethodTypeMethod() {
        return MethodInfoImpl.MethodTypeEnum.METHOD;
    }

    @Override
    public MethodInfo.MethodType newMethodTypeStaticMethod() {
        return MethodInfoImpl.MethodTypeEnum.STATIC_METHOD;
    }

}
