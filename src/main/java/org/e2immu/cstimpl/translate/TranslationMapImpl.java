package org.e2immu.cstimpl.translate;

import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.statement.Statement;
import org.e2immu.cstapi.translate.TranslationMap;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.LocalVariable;
import org.e2immu.cstapi.variable.Variable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class TranslationMapImpl implements TranslationMap {

    public static class Builder implements TranslationMap.Builder {

        private final Map<Variable, Variable> variables = new HashMap<>();
        private final Map<Expression, Expression> expressions = new HashMap<>();
        private final Map<Variable, Expression> variableExpressions = new HashMap<>();
        private final Map<MethodInfo, MethodInfo> methods = new HashMap<>();
        private final Map<Statement, List<Statement>> statements = new HashMap<>();
        private final Map<ParameterizedType, ParameterizedType> types = new HashMap<>();
        private boolean expandDelayedWrappedExpressions;
        private boolean recurseIntoScopeVariables;
        private boolean yieldIntoReturn;
        private boolean translateAgain;

        public Builder() {
        }

        public Builder(TranslationMap other) {
            variables.putAll(other.variables());
            expressions.putAll(other.expressions());
            variableExpressions.putAll(other.variableExpressions());
            methods.putAll(other.methods());
            statements.putAll(other.statements());
            types.putAll(other.types());
            expandDelayedWrappedExpressions = other.expandDelayedWrappedExpressions();
            recurseIntoScopeVariables = other.recurseIntoScopeVariables();
            yieldIntoReturn = other.translateYieldIntoReturn();
            translateAgain = other.translateAgain();
        }

        @Override
        public TranslationMap build() {
            return null;
        }

        @Override
        public Builder setTranslateAgain(boolean translateAgain) {
            return null;
        }

        @Override
        public Builder setRecurseIntoScopeVariables(boolean recurseIntoScopeVariables) {
            return null;
        }

        @Override
        public Builder put(Statement template, Statement actual) {
            return null;
        }

        @Override
        public Builder put(MethodInfo template, MethodInfo actual) {
            return null;
        }

        @Override
        public Builder put(Statement template, List<Statement> statements) {
            return null;
        }

        @Override
        public Builder put(Expression template, Expression actual) {
            return null;
        }

        @Override
        public Builder addVariableExpression(Variable variable, Expression actual) {
            return null;
        }

        @Override
        public Builder renameVariable(Variable variable, Expression actual) {
            return null;
        }

        @Override
        public Builder put(ParameterizedType template, ParameterizedType actual) {
            return null;
        }

        @Override
        public TranslationMap.Builder put(Variable template, Variable actual) {
            return null;
        }

        @Override
        public Builder setYieldToReturn(boolean b) {
            return null;
        }

        @Override
        public Builder setExpandDelayedWrapperExpressions(boolean expandDelayedWrappedExpressions) {
            return null;
        }

        @Override
        public Builder translateMethod(MethodInfo methodInfo) {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    @Override
    public <T> Collector<T, List<T>, List<T>> toList(List<T> original) {
        return new Collector<>() {
            boolean changes;

            @Override
            public Supplier<List<T>> supplier() {
                return () -> new ArrayList<T>(original.size());
            }

            @Override
            public BiConsumer<List<T>, T> accumulator() {
                return (list, t) -> {
                    T inOriginal = original.get(list.size());
                    changes |= inOriginal != t;
                    list.add(t);
                };
            }

            @Override
            public BinaryOperator<List<T>> combiner() {
                return (l1, l2) -> {
                    throw new UnsupportedOperationException("Combiner not implemented");
                };
            }

            @Override
            public Function<List<T>, List<T>> finisher() {
                // we also test for different size: this allows for the removal of objects outside a strict
                // Translation setting (see ParameterizedType.replaceTypeBounds)
                return list -> changes || list.size() != original.size() ? List.copyOf(list) : original;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of();
            }
        };
    }

    @Override
    public <T> Collector<T, Set<T>, Set<T>> toSet(Set<T> original) {
        return new Collector<>() {
            boolean changes;

            @Override
            public Supplier<Set<T>> supplier() {
                return () -> new HashSet<>(original.size());
            }

            @Override
            public BiConsumer<Set<T>, T> accumulator() {
                return (set, t) -> {
                    boolean inOriginal = original.contains(t);
                    changes |= !inOriginal;
                    set.add(t);
                };
            }

            @Override
            public BinaryOperator<Set<T>> combiner() {
                return (l1, l2) -> {
                    throw new UnsupportedOperationException("Combiner not implemented");
                };
            }

            @Override
            public Function<Set<T>, Set<T>> finisher() {
                return set -> changes || set.size() != original.size() ? Set.copyOf(set) : original;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of();
            }
        };
    }

    @Override
    public <K, V> Collector<Map.Entry<K, V>, Map<K, V>, Map<K, V>> toMap(Map<K, V> original) {
        return new Collector<>() {
            boolean changes;

            @Override
            public Supplier<Map<K, V>> supplier() {
                return () -> new HashMap<>(original.size());
            }

            @Override
            public BiConsumer<Map<K, V>, Map.Entry<K, V>> accumulator() {
                return (map, entry) -> {
                    K key = entry.getKey();
                    V inOriginal = original.get(key);
                    V value = entry.getValue();
                    changes |= !Objects.equals(inOriginal, value);
                    map.put(key, value);
                };
            }

            @Override
            public BinaryOperator<Map<K, V>> combiner() {
                return (l1, l2) -> {
                    throw new UnsupportedOperationException("Combiner not implemented");
                };
            }

            @Override
            public Function<Map<K, V>, Map<K, V>> finisher() {
                return map -> changes || map.size() != original.size() ? Map.copyOf(map) : original;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of();
            }
        };
    }
}
