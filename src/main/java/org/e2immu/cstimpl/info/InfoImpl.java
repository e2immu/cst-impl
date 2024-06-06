package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.analysis.Property;
import org.e2immu.cstapi.analysis.Value;
import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.support.SetOnce;
import org.e2immu.support.SetOnceMap;

import java.util.List;
import java.util.Map;

public abstract class InfoImpl implements Element {

    private final SetOnceMap<Property, Value> analysis = new SetOnceMap<>();
    private final SetOnce<List<Comment>> comments = new SetOnce<>();
    private final SetOnce<Source> source = new SetOnce<>();

    @Override
    public Value analysed(Property property) {
        return analysis.getOrDefaultNull(property);
    }

    @Override
    public Value analysedOrDefault(Property property, Value defaultValue) {
        assert defaultValue != null;
        return analysis.getOrDefault(property, defaultValue);
    }

    public void putPropertyValue(Property property, Value value) {
        analysis.put(property, value);
    }

    public void putPropertyValues(Map<Property, Value> propertyValueMap) {
        propertyValueMap.forEach(analysis::put);
    }

    @Override
    public List<Comment> comments() {
        return comments.getOrDefaultNull();
    }

    @Override
    public Source source() {
        return source.getOrDefaultNull();
    }
}
