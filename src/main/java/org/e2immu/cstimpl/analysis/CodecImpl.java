package org.e2immu.cstimpl.analysis;

import org.e2immu.cstapi.analysis.Codec;
import org.e2immu.cstapi.analysis.Property;
import org.e2immu.cstapi.analysis.PropertyValueMap;
import org.e2immu.cstapi.analysis.Value;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.info.*;
import org.e2immu.cstapi.variable.Variable;
import org.parsers.json.Node;
import org.parsers.json.ast.JSONObject;
import org.parsers.json.ast.Literal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodecImpl implements Codec {

    record D(Node s) implements EncodedValue {
    }

    record E(String s) implements EncodedValue {
    }

    @Override
    public boolean decodeBoolean(EncodedValue encodedValue) {
        if (encodedValue instanceof D d && d.s instanceof Literal l) {
            return "true".equals(l.getSource());
        } else throw new UnsupportedOperationException();
    }

    @Override
    public Expression decodeExpression(EncodedValue value) {
        throw new UnsupportedOperationException(); // not implemented here, need parser and context
    }

    @Override
    public FieldInfo decodeFieldInfo(EncodedValue encodedValue) {
        throw new UnsupportedOperationException(); // not implemented here, need type context
    }

    @Override
    public int decodeInt(EncodedValue encodedValue) {
        if (encodedValue instanceof D d && d.s instanceof Literal l) {
            return Integer.parseInt(l.getSource());
        } else throw new UnsupportedOperationException();
    }

    @Override
    public List<EncodedValue> decodeList(EncodedValue encodedValue) {
        return List.of();
    }

    @Override
    public Map<EncodedValue, EncodedValue> decodeMap(EncodedValue encodedValue) {
        return Map.of();
    }

    @Override
    public MethodInfo decodeMethodInfo(EncodedValue encodedValue) {
        throw new UnsupportedOperationException(); // not implemented here, need type context
    }

    @Override
    public ParameterInfo decodeParameterInfo(EncodedValue ev) {
        throw new UnsupportedOperationException(); // not implemented here, need type context
    }

    @Override
    public Set<EncodedValue> decodeSet(EncodedValue encodedValue) {
        return Set.of();
    }

    @Override
    public String decodeString(EncodedValue encodedValue) {
        return "";
    }

    @Override
    public EncodedValue encodeBoolean(boolean value) {
        return new E(Boolean.toString(value));
    }

    @Override
    public EncodedValue encodeExpression(Expression expression) {
        return null;
    }

    @Override
    public EncodedValue encodeInfo(Info info) {
        return null;
    }

    @Override
    public EncodedValue encodeInt(int value) {
        return new E(Integer.toString(value));
    }

    @Override
    public EncodedValue encodeList(List<EncodedValue> encodedValues) {
        String e = encodedValues.stream().map(ev -> ((E) ev).s)
                .collect(Collectors.joining(",", "[", "]"));
        return new E(e);
    }

    @Override
    public EncodedValue encodeMap(Map<EncodedValue, EncodedValue> map) {
        return null;
    }

    @Override
    public EncodedValue encodeSet(Set<EncodedValue> set) {
        String e = set.stream().map(ev -> ((E) ev).s).sorted()
                .collect(Collectors.joining(",", "[", "]"));
        return new E(e);
    }

    @Override
    public EncodedValue encodeString(String string) {
        return new E(quote(string));
    }

    public static String quote(String s) {
        return "\"" + s.replace("\"", "\\\"") + "\"";
    }

    public static String unquote(String s) {
        String s1 = s.substring(1, s.length() - 1);
        return s1.replace("\\\"", "\"");
    }

    @Override
    public EncodedValue encodeVariable(Variable variable) {
        return new E(variable.fullyQualifiedName());
    }

    @Override
    public Stream<PropertyValue> decode(PropertyValueMap pvm, Stream<EncodedPropertyValue> encodedPropertyValueStream) {
        return encodedPropertyValueStream.map(epv -> {
            String key = epv.key();
            Property property = pvm.property(key);
            Class<? extends Value> clazz = property.classOfValue();
            BiFunction<Codec, EncodedValue, Value> decoder = ValueImpl.decoder(clazz);
            D d = (D) epv.encodedValue();
            Value value = decoder.apply(this, d);
            return new PropertyValue(property, value);
        });
    }

    @Override
    public EncodedValue encode(Element info, Stream<EncodedPropertyValue> encodedPropertyValueStream) {
        String fqn;
        if (info instanceof TypeInfo typeInfo) {
            fqn = "T" + typeInfo.fullyQualifiedName();
        } else if (info instanceof MethodInfo methodInfo) {
            fqn = "M" + methodInfo.fullyQualifiedName();
        } else if (info instanceof FieldInfo fieldInfo) {
            fqn = "F" + fieldInfo.fullyQualifiedName();
        } else if (info instanceof ParameterInfo pi) {
            fqn = "P" + pi.fullyQualifiedName();
        } else throw new UnsupportedOperationException();
        String pvStream = encodedPropertyValueStream.map(epv -> '"' + epv.key() + "\":" + ((E) epv.encodedValue()).s)
                .collect(Collectors.joining(",", "{", "}"));
        String all = "{\"fqn\": " + quote(fqn) + ", \"data\":" + pvStream + "}";
        return new E(all);
    }
}
