package org.e2immu.cstimpl.output;


import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.OutputElement;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class OutputBuilderImpl implements OutputBuilder {

    private final List<OutputElement> list = new LinkedList<>();

    @Override
    public OutputBuilder add(OutputElement... outputElements) {
        Collections.addAll(list, outputElements);
        return this;
    }

    @Override
    public OutputBuilder add(OutputBuilder... outputBuilders) {
        Arrays.stream(outputBuilders).flatMap(ob -> ob.list().stream()).forEach(list::add);
        return this;
    }

    @Override
    public List<OutputElement> list() {
        return list;
    }

}
