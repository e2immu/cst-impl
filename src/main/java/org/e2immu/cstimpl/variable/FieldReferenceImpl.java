package org.e2immu.cstimpl.variable;

import org.e2immu.annotation.NotNull;
import org.e2immu.annotation.Nullable;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.VariableExpression;
import org.e2immu.cstapi.info.FieldInfo;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.*;
import org.e2immu.cstimpl.expression.TypeExpressionImpl;
import org.e2immu.cstimpl.expression.VariableExpressionImpl;
import org.e2immu.cstimpl.type.DiamondImpl;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class FieldReferenceImpl extends VariableImpl implements FieldReference {
    @NotNull
    private final FieldInfo fieldInfo;

    @NotNull
    private final Expression scope;

    @Nullable
    private final Variable scopeVariable;

    private final boolean isDefaultScope;

    @NotNull
    private final String fullyQualifiedName;

    public FieldReferenceImpl(FieldInfo fieldInfo) {
        this(fieldInfo, null, null, null);
    }

    public FieldReferenceImpl(FieldInfo fieldInfo,
                              Expression scope) {
        this(fieldInfo, scope, null, null);
    }

    public FieldReferenceImpl(FieldInfo fieldInfo,
                              Expression scope,
                              Variable overrideScopeVariable,
                              ParameterizedType parameterizedType) {
        super(parameterizedType);
        this.fieldInfo = Objects.requireNonNull(fieldInfo);
        if (fieldInfo.isStatic()) {
            // IMPORTANT: the owner doesn't necessarily have a decent identifier, but the field should have one
            this.scope = new TypeExpressionImpl(fieldInfo.owner().asSimpleParameterizedType(), DiamondImpl.NO);
            isDefaultScope = true;
            this.scopeVariable = null;
        } else if (scope == null) {
            scopeVariable = new ThisImpl(fieldInfo.owner());
            this.scope = new VariableExpressionImpl(scopeVariable);
            isDefaultScope = true;
        } else {
            if (scope instanceof VariableExpression ve) {
                if (ve.variable() instanceof This thisVar) {
                    if (thisVar.typeInfo() == fieldInfo.owner()) {
                        this.scope = scope;
                        scopeVariable = ve.variable();
                    } else {
                        scopeVariable = new ThisImpl(fieldInfo.owner());
                        this.scope = new VariableExpressionImpl(scopeVariable);
                    }
                    isDefaultScope = true;
                } else {
                    this.scope = scope;
                    isDefaultScope = false;
                    scopeVariable = ve.variable();
                }
            } else {
                // the scope is not a variable, we must introduce a new scope variable
                this.scope = scope;
                isDefaultScope = false;
                scopeVariable = overrideScopeVariable != null ? overrideScopeVariable : newScopeVariable(scope);
            }
        }
        this.fullyQualifiedName = computeFqn();
        assert (scopeVariable == null) == isStatic;
    }

    private LocalVariable newScopeVariable(Expression scope) {
        Identifier identifier = scope.getIdentifier();
        // in the first iteration, we have
        // "assert identifier instanceof Identifier.PositionalIdentifier;"
        // because it must come from the inspector.
        // but in a InlinedMethod replacement, the scope can literally come from everywhere
        String name = "scope-" + identifier.compact();
        VariableNature vn = new VariableNature.ScopeVariable();
        return new LocalVariable(Set.of(LocalVariableModifier.FINAL), name, scope.parameterizedType(), List.of(), owningType, vn);
    }

    private String computeFqn() {
        if (isStatic() || scopeIsThis()) {
            return fieldInfo.fullyQualifiedName();
        }
        return fieldInfo.fullyQualifiedName() + "#" + scopeVariable.fullyQualifiedName();
    }

    @Override
    public int complexity() {
        if (isStatic()) return 2;
        return 1 + scope.complexity();
    }

    @Override
    public Stream<Variable> variables(DescendModeEnum descendMode) {
        if (descendMode.isYes() && scopeVariable != null) {
            return Stream.concat(Stream.of(this), scopeVariable.variables(descendMode));
        }
        return Stream.of(this);
    }
}
