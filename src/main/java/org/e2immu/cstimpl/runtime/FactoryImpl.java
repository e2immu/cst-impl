package org.e2immu.cstimpl.runtime;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.CompilationUnit;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.expression.*;
import org.e2immu.cstapi.info.*;
import org.e2immu.cstapi.runtime.Factory;
import org.e2immu.cstapi.statement.*;
import org.e2immu.cstapi.translate.TranslationMap;
import org.e2immu.cstapi.type.*;
import org.e2immu.cstapi.variable.*;
import org.e2immu.cstimpl.element.*;
import org.e2immu.cstimpl.expression.*;
import org.e2immu.cstimpl.expression.util.PrecedenceEnum;
import org.e2immu.cstimpl.info.*;
import org.e2immu.cstimpl.statement.*;
import org.e2immu.cstimpl.translate.TranslationMapImpl;
import org.e2immu.cstimpl.type.DiamondEnum;
import org.e2immu.cstimpl.type.ParameterizedTypeImpl;
import org.e2immu.cstimpl.type.TypeParameterImpl;
import org.e2immu.cstimpl.type.WildcardEnum;
import org.e2immu.cstimpl.util.IntUtil;
import org.e2immu.cstimpl.variable.DependentVariableImpl;
import org.e2immu.cstimpl.variable.FieldReferenceImpl;
import org.e2immu.cstimpl.variable.LocalVariableImpl;
import org.e2immu.cstimpl.variable.ThisImpl;
import org.e2immu.support.Either;

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
    public Assignment newAssignment(Expression target, Expression value) {
        return new AssignmentImpl(target, value);
    }

    @Override
    public Assignment.Builder newAssignmentBuilder() {
        return new AssignmentImpl.Builder();
    }

    @Override
    public Equals newEquals(Expression lhs, Expression rhs) {
        MethodInfo operator = lhs.isNumeric() && rhs.isNumeric() ? equalsOperatorInt()
                : equalsOperatorObject();
        return new EqualsImpl(List.of(), null, operator, PrecedenceEnum.EQUALITY, lhs, rhs, booleanParameterizedType());
    }

    @Override
    public InstanceOf newInstanceOf(ParameterizedType parameterizedType, Expression expression, LocalVariable patternVariable) {
        return null;
    }

    @Override
    public BinaryOperator.Builder newBinaryOperatorBuilder() {
        return new BinaryOperatorImpl.Builder();
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
        return new CastImpl(parameterizedType, e);
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
    public MethodCall newMethodCall(Expression object, MethodInfo methodInfo, List<Expression> parameterExpressions) {
        return new MethodCallImpl.Builder()
                .setObject(object)
                .setMethodInfo(methodInfo)
                .setParameterExpressions(parameterExpressions)
                .build();
    }

    @Override
    public MethodCall.Builder newMethodCallBuilder() {
        return new MethodCallImpl.Builder();
    }

    @Override
    public TypeExpression newTypeExpression(ParameterizedType parameterizedType, Diamond diamond) {
        return new TypeExpressionImpl(parameterizedType, diamond);
    }

    @Override
    public ConstructorCall.Builder newConstructorCallBuilder() {
        return new ConstructorCallImpl.Builder();
    }

    @Override
    public IfElseStatement.Builder newIfElseBuilder() {
        return new IfElseStatementImpl.Builder();
    }

    @Override
    public ExpressionAsStatement newExpressionAsStatement(Expression expression) {
        return new ExpressionAsStatementImpl(expression);
    }

    @Override
    public ExpressionAsStatement.Builder newExpressionAsStatementBuilder() {
        return new ExpressionAsStatementImpl.Builder();
    }

    @Override
    public ThrowStatement.Builder newThrowBuilder() {
        return new ThrowStatementImpl.Builder();
    }

    @Override
    public AssertStatement.Builder newAssertStatementBuilder() {
        return new AssertStatementImpl.Builder();
    }

    @Override
    public ReturnStatement newReturnStatement(Expression expression) {
        return new ReturnStatementImpl(expression);
    }

    @Override
    public ReturnStatement.Builder newReturnBuilder() {
        return new ReturnStatementImpl.Builder();
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
    public VariableExpression newVariableExpression(Variable variable) {
        return new VariableExpressionImpl(variable);
    }

    @Override
    public StringConstant newStringConstant(String string) {
        return new StringConstantImpl(this, string);
    }

    @Override
    public ConstructorCall objectCreation(Expression scope, MethodInfo constructor, ParameterizedType parameterizedType, Diamond diamond, List<Expression> parameterExpressions) {
        return null;
    }

    @Override
    public TypeInfo newTypeInfo(TypeInfo typeInfo, String capitalized) {
        return new TypeInfoImpl(typeInfo, capitalized);
    }

    @Override
    public TypeInfo newTypeInfo(CompilationUnit cu, String simpleName) {
        return new TypeInfoImpl(cu, simpleName);
    }

    @Override
    public ParameterizedType newParameterizedType(TypeInfo typeInfo, List<ParameterizedType> newParameters) {
        return new ParameterizedTypeImpl(typeInfo, null, newParameters, 0, null);
    }

    @Override
    public ParameterizedType newParameterizedType(TypeInfo typeInfo, int arrays) {
        return new ParameterizedTypeImpl(typeInfo, null, List.of(), arrays, null);
    }

    @Override
    public ParameterizedType newParameterizedType(TypeParameter typeParameter, int arrays, Wildcard wildCard) {
        return new ParameterizedTypeImpl(null, typeParameter, List.of(), arrays, wildCard);
    }

    @Override
    public ParameterizedType newParameterizedType(TypeInfo typeInfo, int arrays, Wildcard wildCard, List<ParameterizedType> parameters) {
        return new ParameterizedTypeImpl(typeInfo, null, parameters, arrays, wildCard);
    }

    @Override
    public TypeParameter newTypeParameter(int index, String simpleName, TypeInfo owner) {
        return new TypeParameterImpl(index, simpleName, Either.left(owner));
    }

    @Override
    public TypeParameter newTypeParameter(int index, String simpleName, MethodInfo owner) {
        return new TypeParameterImpl(index, simpleName, Either.right(owner));
    }

    @Override
    public ParameterizedType parameterizedTypeWildcard() {
        return ParameterizedTypeImpl.WILDCARD_PARAMETERIZED_TYPE;
    }

    @Override
    public ParameterizedType parameterizedTypeReturnTypeOfConstructor() {
        return ParameterizedTypeImpl.RETURN_TYPE_OF_CONSTRUCTOR;
    }

    @Override
    public Diamond diamondYes() {
        return DiamondEnum.YES;
    }

    @Override
    public Diamond diamondShowAll() {
        return DiamondEnum.SHOW_ALL;
    }

    @Override
    public Diamond diamondNo() {
        return DiamondEnum.NO;
    }

    @Override
    public Expression constructorCallWithArrayInitializer(MethodInfo constructor, ParameterizedType returnType, List<Object> of, ArrayInitializer initializer) {
        return null;
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
    public CharConstant newChar(char c) {
        return new CharConstantImpl(this, c);
    }

    @Override
    public This newThis(TypeInfo typeInfo) {
        return new ThisImpl(typeInfo);
    }

    @Override
    public DependentVariable newDependentVariable(Expression array, Expression index) {
        return DependentVariableImpl.create(this, array, index);
    }

    @Override
    public Expression newMultiExpressions(List<Expression> newExpressions) {
        return null;
    }

    @Override
    public BooleanConstant newBoolean(boolean value) {
        return new BooleanConstantImpl(this, value);
    }

    @Override
    public IntConstant intZero() {
        return zero;
    }

    @Override
    public IntConstant intOne() {
        return one;
    }

    @Override
    public IntConstant intMinusOne() {
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
    public Precedence precedenceUnary() {
        return PrecedenceEnum.UNARY;
    }

    @Override
    public Precedence precedenceEquality() {
        return PrecedenceEnum.EQUALITY;
    }

    @Override
    public Expression nullValue(TypeInfo typeInfo) {
        if (typeInfo != null) {
            if (typeInfo.isBoolean()) return newBoolean(false);
            if (typeInfo.isInt()) return zero;
            if (typeInfo.isLong()) return newLong(0L);
            if (typeInfo.isShort()) return newShort((short) 0);
            if (typeInfo.isByte()) return newByte((byte) 0);
            if (typeInfo.isFloat()) return newFloat(0);
            if (typeInfo.isDouble()) return newDouble(0);
            if (typeInfo.isChar()) return newChar('\0');
        }
        return nullConstant();
    }

    @Override
    public Precedence precedenceGreaterThan() {
        return PrecedenceEnum.EQUALITY;
    }

    @Override
    public Precedence precedenceAnd() {
        return PrecedenceEnum.AND;
    }

    @Override
    public Precedence precedenceOr() {
        return PrecedenceEnum.OR;
    }

    @Override
    public Precedence precedenceAssignment() {
        return PrecedenceEnum.ASSIGNMENT;
    }

    @Override
    public Precedence precedenceMultiplicative() {
        return PrecedenceEnum.MULTIPLICATIVE;
    }

    @Override
    public Precedence precedenceAdditive() {
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
        return new DoubleConstantImpl(this, d);
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
    public TryStatement.Builder newTryBuilder() {
        return new TryStatementImpl.Builder();
    }

    @Override
    public TryStatement.CatchClause.Builder newCatchClauseBuilder() {
        return new TryStatementImpl.CatchClauseImpl.Builder();
    }

    @Override
    public Lambda newLambda(ParameterizedType abstractFunctionalType, ParameterizedType implementation, ParameterizedType concreteReturnType, List<Lambda.OutputVariant> outputVariants) {
        return null;
    }

    @Override
    public FieldReference newFieldReference(FieldInfo fieldInfo) {
        return new FieldReferenceImpl(fieldInfo);
    }

    @Override
    public FieldReference newFieldReference(FieldInfo fieldInfo, Expression scope, ParameterizedType concreteReturnType) {
        return new FieldReferenceImpl(fieldInfo, scope, null, concreteReturnType);
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

    @Override
    public Comment newSingleLineComment(String comment) {
        return new SingleLineComment(comment);
    }

    @Override
    public Comment newMultilineComment(String comment) {
        return new MultiLineComment(comment);
    }

    @Override
    public Source newParserSource(Element parent, String index, int beginLine, int beginPos, int endLine, int endPos) {
        return new SourceImpl(parent, index, beginLine, beginPos, endLine, endPos);
    }

    @Override
    public CompilationUnit.Builder newCompilationUnitBuilder() {
        return new CompilationUnitImpl.Builder();
    }

    @Override
    public Wildcard wildcardExtends() {
        return WildcardEnum.EXTENDS;
    }

    @Override
    public Wildcard wildcardSuper() {
        return WildcardEnum.SUPER;
    }

    @Override
    public AnnotationExpression.Builder newAnnotationExpressionBuilder() {
        return new AnnotationExpressionImpl.Builder();
    }

    @Override
    public MethodInfo.MethodType newMethodTypeAbstractMethod() {
        return MethodInfoImpl.MethodTypeEnum.ABSTRACT_METHOD;
    }

    @Override
    public MethodInfo.MethodType newMethodTypeDefaultMethod() {
        return MethodInfoImpl.MethodTypeEnum.DEFAULT_METHOD;
    }

    @Override
    public EnclosedExpression newEnclosedExpression(Expression inner) {
        return new EnclosedExpressionImpl(inner);
    }

    @Override
    public FieldModifier fieldModifierFinal() {
        return FieldModifierEnum.FINAL;
    }

    @Override
    public FieldModifier fieldModifierPublic() {
        return FieldModifierEnum.PUBLIC;
    }

    @Override
    public FieldModifier fieldModifierStatic() {
        return FieldModifierEnum.STATIC;
    }

    @Override
    public FieldModifier fieldModifierTransient() {
        return FieldModifierEnum.TRANSIENT;
    }

    @Override
    public FieldModifier fieldModifierPrivate() {
        return FieldModifierEnum.PRIVATE;
    }

    @Override
    public FieldModifier fieldModifierProtected() {
        return FieldModifierEnum.PROTECTED;
    }

    @Override
    public FieldModifier fieldModifierVolatile() {
        return FieldModifierEnum.VOLATILE;
    }

    @Override
    public Access accessPackage() {
        return InspectionImpl.AccessEnum.PACKAGE;
    }

    @Override
    public Access accessPrivate() {
        return InspectionImpl.AccessEnum.PRIVATE;
    }

    @Override
    public Access accessProtected() {
        return InspectionImpl.AccessEnum.PROTECTED;
    }

    @Override
    public Access accessPublic() {
        return InspectionImpl.AccessEnum.PUBLIC;
    }

    @Override
    public FieldInfo newFieldInfo(String name, boolean isStatic, ParameterizedType parameterizedType, TypeInfo owner) {
        return new FieldInfoImpl(name, isStatic, parameterizedType, owner);
    }

    @Override
    public TypeNature typeNatureAnnotation() {
        return TypeNatureEnum.ANNOTATION;
    }

    @Override
    public TypeNature typeNatureClass() {
        return TypeNatureEnum.CLASS;
    }

    @Override
    public TypeNature typeNatureEnum() {
        return TypeNatureEnum.ENUM;
    }

    @Override
    public TypeNature typeNatureInterface() {
        return TypeNatureEnum.INTERFACE;
    }

    @Override
    public TypeNature typeNatureRecord() {
        return TypeNatureEnum.RECORD;
    }

    @Override
    public TypeModifier typeModifierAbstract() {
        return TypeModifierEnum.ABSTRACT;
    }

    @Override
    public TypeModifier typeModifierFinal() {
        return TypeModifierEnum.FINAL;
    }

    @Override
    public TypeModifier typeModifierNonSealed() {
        return TypeModifierEnum.NON_SEALED;
    }

    @Override
    public TypeModifier typeModifierPrivate() {
        return TypeModifierEnum.PRIVATE;
    }

    @Override
    public TypeModifier typeModifierProtected() {
        return TypeModifierEnum.PROTECTED;
    }

    @Override
    public TypeModifier typeModifierPublic() {
        return TypeModifierEnum.PUBLIC;
    }

    @Override
    public TypeModifier typeModifierSealed() {
        return TypeModifierEnum.SEALED;
    }

    @Override
    public TypeModifier typeModifierStatic() {
        return TypeModifierEnum.STATIC;
    }

    @Override
    public MethodModifier methodModifierAbstract() {
        return MethodModifierEnum.ABSTRACT;
    }

    @Override
    public MethodModifier methodModifierDefault() {
        return MethodModifierEnum.DEFAULT;
    }

    @Override
    public MethodModifier methodModifierFinal() {
        return MethodModifierEnum.FINAL;
    }

    @Override
    public MethodModifier methodModifierPrivate() {
        return MethodModifierEnum.PRIVATE;
    }

    @Override
    public MethodModifier methodModifierProtected() {
        return MethodModifierEnum.PROTECTED;
    }

    @Override
    public MethodModifier methodModifierPublic() {
        return MethodModifierEnum.PUBLIC;
    }

    @Override
    public MethodModifier methodModifierStatic() {
        return MethodModifierEnum.STATIC;
    }

    @Override
    public MethodModifier methodModifierSynchronized() {
        return MethodModifierEnum.SYNCHRONIZED;
    }

    @Override
    public MethodInfo.MethodType newMethodTypeConstructor() {
        return MethodInfoImpl.MethodTypeEnum.CONSTRUCTOR;
    }

    @Override
    public ForEachStatement.Builder newForEachBuilder() {
        return new ForEachStatementImpl.Builder();
    }

    @Override
    public WhileStatement.Builder newWhileBuilder() {
        return new WhileStatementImpl.Builder();
    }

    @Override
    public YieldStatement.Builder newYieldBuilder() {
        return new YieldStatementImpl.Builder();
    }

    @Override
    public DoStatement.Builder newDoBuilder() {
        return new DoStatementImpl.Builder();
    }

    @Override
    public VariableExpression.Builder newVariableExpressionBuilder() {
        return new VariableExpressionImpl.Builder();
    }

    @Override
    public SynchronizedStatement.Builder newSynchronizedBuilder() {
        return new SynchronizedStatementImpl.Builder();
    }
}

