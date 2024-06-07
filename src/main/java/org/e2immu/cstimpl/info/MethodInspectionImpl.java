package org.e2immu.cstimpl.info;

import org.e2immu.annotation.Fluent;
import org.e2immu.cstapi.info.Access;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.MethodModifier;
import org.e2immu.cstapi.info.ParameterInfo;
import org.e2immu.cstapi.statement.Block;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.type.TypeParameter;
import org.e2immu.cstimpl.type.DiamondEnum;
import org.e2immu.support.SetOnce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MethodInspectionImpl extends InspectionImpl implements MethodInspection {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodInspectionImpl.class);

    private final ParameterizedType returnType;
    private final OperatorType operatorType;
    private final Block methodBody;
    private final String fullyQualifiedName;
    private final Set<MethodInfo> overrides;

    public MethodInspectionImpl(Inspection inspection,
                                ParameterizedType returnType,
                                OperatorType operatorType,
                                Block methodBody,
                                String fullyQualifiedName,
                                Set<MethodInfo> overrides) {
        super(inspection.access(), inspection.comments(), inspection.source(), inspection.isSynthetic(),
                inspection.annotations());
        this.returnType = returnType;
        this.operatorType = operatorType;
        this.methodBody = methodBody;
        this.fullyQualifiedName = fullyQualifiedName;
        this.overrides = overrides;
    }

    @Override
    public Set<MethodInfo> overrides() {
        return overrides;
    }

    @Override
    public ParameterizedType returnType() {
        return returnType;
    }

    @Override
    public OperatorType operatorType() {
        return operatorType;
    }

    @Override
    public Block methodBody() {
        return methodBody;
    }

    @Override
    public String fullyQualifiedName() {
        return fullyQualifiedName;
    }

    public static class Builder extends InspectionImpl.Builder implements MethodInspection, MethodInfo.Builder {
        private ParameterizedType returnType;
        private OperatorType operatorType;
        private Block methodBody;
        private final SetOnce<String> fullyQualifiedName = new SetOnce<>();
        private final List<ParameterInfo> parameters = new ArrayList<>();
        private final List<TypeParameter> typeParameters = new ArrayList<>();
        private final Set<MethodInfo> overrides = new HashSet<>();
        private final MethodInfoImpl methodInfo;

        public Builder(MethodInfoImpl methodInfo) {
            this.methodInfo = methodInfo;
            if (methodInfo.isStatic()) {
                addMethodModifier(MethodModifierEnum.STATIC);
            }
        }

        @Fluent
        public Builder setReturnType(ParameterizedType returnType) {
            this.returnType = returnType;
            return this;
        }

        @Override
        public ParameterInfo addParameter(String name, ParameterizedType type) {
            ParameterInfo pi = new ParameterInfoImpl(methodInfo, parameters.size(), name, type);
            parameters.add(pi);
            return pi;
        }

        @Override
        public MethodInfo.Builder addTypeParameter(TypeParameter typeParameter) {
            assert typeParameter.isMethodTypeParameter();
            assert typeParameter.getOwner().getRight() == methodInfo;
            typeParameters.add(typeParameter);
            return this;
        }

        @Override
        public Set<MethodInfo> overrides() {
            return overrides;
        }

        @Override
        public ParameterizedType returnType() {
            return returnType;
        }

        @Override
        public OperatorType operatorType() {
            return operatorType;
        }

        @Fluent
        public Builder setOperatorType(OperatorType operatorType) {
            this.operatorType = operatorType;
            return this;
        }

        @Override
        public Block methodBody() {
            return methodBody;
        }

        @Override
        public MethodInfo.Builder commitParameters() {
            fullyQualifiedName.set(computeFQN());
            return this;
        }

        private String computeFQN() {
            String owner = methodInfo.typeInfo().fullyQualifiedName();
            try {
                return owner + "." + methodInfo.name() + "(" + parameters.stream()
                        .map(p -> p.parameterizedType().printForMethodFQN(p.isVarArgs(), DiamondEnum.SHOW_ALL))
                        .collect(Collectors.joining(",")) + ")";
            } catch (RuntimeException re) {
                LOGGER.error("Cannot compute fully qualified method name, type {}, method {}, {} params",
                        owner, methodInfo.name(), parameters.size());
                throw re;
            }
        }

        @Override
        public void commit() {
            if (!fullyQualifiedName.isSet()) commitParameters();
            MethodInspection mi = new MethodInspectionImpl(this, returnType, operatorType, methodBody,
                    fullyQualifiedName.get(), Set.copyOf(overrides));
            methodInfo.commit(mi);
        }

        @Fluent
        public Builder setMethodBody(Block methodBody) {
            this.methodBody = methodBody;
            return this;
        }

        @Override
        public MethodInfo.Builder addMethodModifier(MethodModifier methodModifier) {
            return null;
        }

        @Override
        public MethodInfo.Builder addAndCommitParameter(String name, ParameterizedType type) {
            return null;
        }

        @Override
        public String fullyQualifiedName() {
            return fullyQualifiedName.get("FQN has not yet been computed");
        }

        @Fluent
        public Builder addParameter(ParameterInfoImpl pi) {
            parameters.add(pi);
            return this;
        }
    }

}
