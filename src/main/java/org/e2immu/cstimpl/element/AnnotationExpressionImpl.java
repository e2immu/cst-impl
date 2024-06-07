package org.e2immu.cstimpl.element;

import org.e2immu.cstapi.expression.AnnotationExpression;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.Qualification;
import org.e2immu.cstimpl.output.OutputBuilderImpl;
import org.e2immu.cstimpl.output.SymbolEnum;
import org.e2immu.cstimpl.output.TextEnum;
import org.e2immu.cstimpl.output.TypeNameImpl;

import java.util.List;

public class AnnotationExpressionImpl implements AnnotationExpression {
    private final TypeInfo typeInfo;

    private final List<KV> keyValuePairs;

    public AnnotationExpressionImpl(TypeInfo typeInfo, List<KV> keyValuePairs) {
        this.typeInfo = typeInfo;
        this.keyValuePairs = keyValuePairs;
    }

    @Override
    public TypeInfo typeInfo() {
        return typeInfo;
    }

    @Override
    public List<KV> keyValuePairs() {
        return keyValuePairs;
    }

    @Override
    public OutputBuilder print(Qualification qualification) {
        OutputBuilder outputBuilder = new OutputBuilderImpl().add(SymbolEnum.AT)
                .add(TypeNameImpl.typeName(typeInfo, qualification.qualifierRequired(typeInfo)));
        if (!keyValuePairs.isEmpty()) {
            outputBuilder.add(SymbolEnum.LEFT_PARENTHESIS)
                    .add(keyValuePairs.stream()
                            .map(kv ->
                                    new OutputBuilderImpl().addIf(kv.keyIsDefault(), new TextEnum(kv.key()))
                                            .addIf(kv.keyIsDefault(), SymbolEnum.assignment("="))
                                            .add(kv.value().print(qualification)))
                            .collect(OutputBuilderImpl.joining(SymbolEnum.COMMA)))
                    .add(SymbolEnum.RIGHT_PARENTHESIS);
        }
        return outputBuilder;
    }
}
