package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.expression.AnnotationExpression;
import org.e2immu.cstapi.info.Access;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.info.TypeModifier;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.type.TypeNature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TypeInspectionImpl extends InspectionImpl implements TypeInspection {
    private final Set<TypeModifier> typeModifiers;
    private final List<MethodInfo> methods;
    private final List<MethodInfo> constructors;
    private final ParameterizedType parentClass;
    private final TypeNature typeNature;
    private final MethodInfo singleAbstractMethod;
    private final List<ParameterizedType> interfacesImplemented;
    private final List<TypeInfo> subTypes;

    public TypeInspectionImpl(Inspection inspection,
                              Set<TypeModifier> typeModifiers,
                              List<MethodInfo> methods,
                              List<MethodInfo> constructors,
                              ParameterizedType parentClass,
                              TypeNature typeNature,
                              MethodInfo singleAbstractMethod,
                              List<ParameterizedType> interfacesImplemented,
                              List<TypeInfo> subTypes) {
        super(inspection.access(), inspection.comments(), inspection.source(), inspection.isSynthetic(), inspection.annotations());
        this.typeModifiers = typeModifiers;
        this.methods = methods;
        this.constructors = constructors;
        this.parentClass = parentClass;
        this.typeNature = typeNature;
        this.singleAbstractMethod = singleAbstractMethod;
        this.interfacesImplemented = interfacesImplemented;
        this.subTypes = subTypes;
    }

    @Override
    public Stream<MethodInfo> methodStream(TypeInfo.Methods methods) {
        return this.methods.stream();
    }

    @Override
    public List<MethodInfo> constructors() {
        return constructors;
    }

    @Override
    public TypeNature typeNature() {
        return typeNature;
    }

    @Override
    public ParameterizedType parentClass() {
        return parentClass;
    }

    @Override
    public List<ParameterizedType> interfacesImplemented() {
        return interfacesImplemented;
    }

    @Override
    public MethodInfo singleAbstractMethod() {
        return singleAbstractMethod;
    }

    @Override
    public List<TypeInfo> subTypes() {
        return subTypes;
    }

    public static class Builder extends InspectionImpl.Builder implements TypeInspection, TypeInfo.Builder {
        private final Set<TypeModifier> typeModifiers = new HashSet<>();
        private final List<MethodInfo> methods = new ArrayList<>();
        private final List<MethodInfo> constructors = new ArrayList<>();
        private final List<ParameterizedType> interfacesImplemented = new ArrayList<>();
        private final List<TypeInfo> subTypes = new ArrayList<>();
        private ParameterizedType parentClass;
        private TypeNature typeNature;
        private MethodInfo singleAbstractMethod;
        private final TypeInfoImpl typeInfo;

        public Builder(TypeInfoImpl typeInfo) {
            this.typeInfo = typeInfo;
        }

        @Override
        public TypeInfo.Builder addSubType(TypeInfo subType) {
            subTypes.add(subType);
            return this;
        }

        @Override
        public TypeInfo.Builder addTypeModifier(TypeModifier typeModifier) {
            typeModifiers.add(typeModifier);
            return this;
        }

        @Override
        public TypeInfo.Builder addMethod(MethodInfo methodInfo) {
            methods.add(methodInfo);
            return this;
        }

        @Override
        public TypeInfo.Builder setTypeNature(TypeNature typeNature) {
            this.typeNature = typeNature;
            return this;
        }

        @Override
        public TypeInfo.Builder setParentClass(ParameterizedType parentClass) {
            this.parentClass = parentClass;
            return this;
        }

        @Override
        public TypeInfo.Builder addInterfaceImplemented(ParameterizedType interfaceImplemented) {
            this.interfacesImplemented.add(interfaceImplemented);
            return this;
        }

        @Override
        public void commit() {
            TypeInspection ti = new TypeInspectionImpl(this, Set.copyOf(typeModifiers), List.copyOf(methods),
                    List.copyOf(constructors), parentClass, typeNature, singleAbstractMethod,
                    List.copyOf(interfacesImplemented), List.copyOf(subTypes));
            typeInfo.commit(ti);
        }

        @Override
        public Stream<MethodInfo> methodStream(TypeInfo.Methods methods) {
            return this.methods.stream(); // FIXME
        }

        @Override
        public List<MethodInfo> constructors() {
            return constructors;
        }

        @Override
        public TypeNature typeNature() {
            return typeNature;
        }

        @Override
        public ParameterizedType parentClass() {
            return parentClass;
        }

        @Override
        public List<ParameterizedType> interfacesImplemented() {
            return interfacesImplemented;
        }

        @Override
        public MethodInfo singleAbstractMethod() {
            return singleAbstractMethod;
        }

        @Override
        public List<TypeInfo> subTypes() {
            return subTypes;
        }
    }
}
