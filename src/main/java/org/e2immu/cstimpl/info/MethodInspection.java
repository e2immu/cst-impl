package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.statement.Block;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.type.TypeParameter;

import java.util.List;
import java.util.Set;

public interface MethodInspection extends Inspection {
    Set<MethodInfo> overrides();

    List<TypeParameter> typeParameters();

    enum OperatorType {
        NONE, INFIX, PREFIX, POSTFIX,
    }

    ParameterizedType returnType();

    OperatorType operatorType();

    Block methodBody();

    String fullyQualifiedName();
}
