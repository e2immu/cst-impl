package org.e2immu.cstimpl.output;


import org.e2immu.cstapi.output.OutputBuilder;
import org.e2immu.cstapi.output.OutputElement;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

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


    public static Collector<OutputBuilder, OutputBuilder, OutputBuilder> joining() {
        return joining(Space.NONE, Space.NONE, Space.NONE, Guide.defaultGuideGenerator());
    }

    public static Collector<OutputBuilder, OutputBuilder, OutputBuilder> joining(OutputElement separator) {
        return joining(separator, Space.NONE, Space.NONE, Guide.defaultGuideGenerator());
    }

    public static Collector<OutputBuilder, OutputBuilder, OutputBuilder> joining(OutputElement separator,
                                                                                 Guide.GuideGenerator guideGenerator) {
        return joining(separator, Space.NONE, Space.NONE, guideGenerator);
    }

    public static Collector<OutputBuilder, OutputBuilder, OutputBuilder> joining(OutputElement separator,
                                                                                 OutputElement start,
                                                                                 OutputElement end,
                                                                                 Guide.GuideGenerator guideGenerator) {
        return new Collector<>() {
            private final AtomicInteger countMid = new AtomicInteger();

            @Override
            public Supplier<OutputBuilder> supplier() {
                return OutputBuilder::new;
            }

            @Override
            public BiConsumer<OutputBuilder, OutputBuilder> accumulator() {
                return (a, b) -> {
                    if (!b.isEmpty()) {
                        if (a.notStart()) { // means: not empty, not only guides
                            if (separator != Space.NONE) a.add(separator);
                            a.add(guideGenerator.mid());
                            countMid.incrementAndGet();
                        }
                        a.add(b);
                    }
                };
            }

            @Override
            public BinaryOperator<OutputBuilder> combiner() {
                return (a, b) -> {
                    if (a.isEmpty()) return b;
                    if (b.isEmpty()) return a;
                    if (separator != Space.NONE) a.add(separator);
                    countMid.incrementAndGet();
                    return a.add(guideGenerator.mid()).add(b);
                };
            }

            @Override
            public Function<OutputBuilder, OutputBuilder> finisher() {
                return t -> {
                    OutputBuilder result = new OutputBuilder();
                    if (start != Space.NONE) result.add(start);
                    if (countMid.get() > 0 || guideGenerator.keepGuidesWithoutMid()) {
                        result.add(guideGenerator.start());
                        result.add(t);
                        result.add(guideGenerator.end());
                    } else {
                        result.add(t); // without the guides
                    }
                    if (end != Space.NONE) result.add(end);
                    return result;
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(Characteristics.CONCURRENT);
            }
        };
    }

}
