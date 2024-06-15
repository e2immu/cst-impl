package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.analysis.Property;
import org.e2immu.cstapi.analysis.Value;
import org.e2immu.cstapi.element.Comment;
import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.variable.DescendModeEnum;
import org.e2immu.support.SetOnce;
import org.e2immu.support.SetOnceMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class InfoImpl implements Element {

    private final SetOnceMap<Property, Value> analysis = new SetOnceMap<>();
    private final SetOnce<List<Comment>> comments = new SetOnce<>();
    private final SetOnce<Source> source = new SetOnce<>();

    @SuppressWarnings("unchecked")
    @Override
    public <V extends Value> V analysedOrDefault(Property property, V defaultValue) {
        assert defaultValue != null;
        return (V) analysis.getOrDefault(property, defaultValue);
    }

    @Override
    public void setAnalyzed(Property property, Value value) {
        assert property.classOfValue().isAssignableFrom(value.getClass());
        analysis.put(property, value);
    }

    @Override
    public List<Comment> comments() {
        return comments.getOrDefaultNull();
    }

    @Override
    public Source source() {
        return source.getOrDefaultNull();
    }

    @Override
    public Stream<Variable> variableStreamDescend() {
        return variables(DescendModeEnum.YES);
    }

    @Override
    public Stream<Variable> variableStreamDoNotDescend() {
        return variables(DescendModeEnum.NO);
    }
}
