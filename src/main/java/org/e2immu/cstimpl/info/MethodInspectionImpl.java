package org.e2immu.cstimpl.info;

import org.e2immu.annotation.Fluent;
import org.e2immu.cstapi.info.ParameterInfo;
import org.e2immu.cstapi.statement.Block;
import org.e2immu.cstapi.type.ParameterizedType;

import java.util.ArrayList;
import java.util.List;

public class MethodInspectionImpl extends InspectionImpl implements MethodInspection {
    private final ParameterizedType returnType;
    private final OperatorType operatorType;
    private final Block methodBody;
    private final String fullyQualifiedName;

    public MethodInspectionImpl(Inspection inspection,
                                ParameterizedType returnType,
                                OperatorType operatorType,
                                Block methodBody,
                                String fullyQualifiedName) {
        super(inspection.access(), inspection.comments(), inspection.source(), inspection.isSynthetic(),
                inspection.annotations());
        this.returnType = returnType;
        this.operatorType = operatorType;
        this.methodBody = methodBody;
        this.fullyQualifiedName = fullyQualifiedName;
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

    public static class Builder extends InspectionImpl.Builder implements MethodInspection {
        private ParameterizedType returnType;
        private OperatorType operatorType;
        private Block methodBody;
        private String fullyQualifiedName;
        private List<ParameterInfo> parameters = new ArrayList<>();

        @Fluent
        public Builder setReturnType(ParameterizedType returnType) {
            this.returnType = returnType;
            return this;
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

        public MethodInspectionImpl build() {
            return new MethodInspectionImpl(this, returnType, operatorType, methodBody, fullyQualifiedName);
        }

        @Override
        public Block methodBody() {
            return methodBody;
        }

        @Fluent
        public Builder setMethodBody(Block methodBody) {
            this.methodBody = methodBody;
            return this;
        }

        @Override
        public String fullyQualifiedName() {
            return fullyQualifiedName;
        }

        @Fluent
        public Builder addParameter(ParameterInfoImpl pi) {
            parameters.add(pi);
            return this;
        }
    }

}
