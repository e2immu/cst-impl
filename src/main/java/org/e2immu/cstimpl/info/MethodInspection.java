package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.statement.Block;
import org.e2immu.cstapi.type.ParameterizedType;

public interface MethodInspection extends Inspection {
    enum OperatorType {
        NONE, INFIX, PREFIX, POSTFIX,
    }

    ParameterizedType returnType();

    OperatorType operatorType();

    Block methodBody();

    String fullyQualifiedName();
}
