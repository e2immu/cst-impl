package org.e2immu.cstimpl.translate;

import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.expression.MethodCall;
import org.e2immu.cstapi.expression.VariableExpression;
import org.e2immu.cstapi.info.MethodInfo;
import org.e2immu.cstapi.statement.Statement;
import org.e2immu.cstapi.translate.TranslationMap;
import org.e2immu.cstapi.type.ParameterizedType;
import org.e2immu.cstapi.variable.FieldReference;
import org.e2immu.cstapi.variable.LocalVariable;
import org.e2immu.cstapi.variable.Variable;
import org.e2immu.cstimpl.expression.VariableExpressionImpl;
import org.e2immu.cstimpl.type.ParameterizedTypeImpl;
import org.e2immu.cstimpl.variable.FieldReferenceImpl;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TranslationMapImpl implements TranslationMap {

    private final Map<? extends Variable, ? extends Variable> variables;
    private final Map<MethodInfo, MethodInfo> methods;
    private final Map<? extends Expression, ? extends Expression> expressions;
    private final Map<? extends Statement, List<Statement>> statements;
    private final Map<ParameterizedType, ParameterizedType> types;
    private final Map<LocalVariable, LocalVariable> localVariables;
    private final Map<? extends Variable, ? extends Expression> variableExpressions;
    private final boolean expandDelayedWrappedExpressions;
    private final boolean recurseIntoScopeVariables;
    private final boolean yieldIntoReturn;
    private final boolean translateAgain;
    private final ModificationTimesHandler modificationTimesHandler;

    private TranslationMapImpl(Map<? extends Statement, List<Statement>> statements,
                               Map<? extends Expression, ? extends Expression> expressions,
                               Map<? extends Variable, ? extends Expression> variableExpressions,
                               Map<? extends Variable, ? extends Variable> variables,
                               Map<MethodInfo, MethodInfo> methods,
                               Map<ParameterizedType, ParameterizedType> types,
                               ModificationTimesHandler modificationTimesHandler,
                               boolean expandDelayedWrappedExpressions,
                               boolean recurseIntoScopeVariables,
                               boolean yieldIntoReturn,
                               boolean translateAgain) {
        this.variables = variables;
        this.expressions = expressions;
        this.variableExpressions = variableExpressions;
        this.statements = statements;
        this.methods = methods;
        this.types = types;//checkForCycles(types);
        this.yieldIntoReturn = yieldIntoReturn;
        localVariables = variables.entrySet().stream()
                .filter(e -> e.getKey() instanceof LocalVariable && e.getValue() instanceof LocalVariable)
                .collect(Collectors.toMap(e -> ((LocalVariable) e.getKey()), e -> ((LocalVariable) e.getValue())));
        this.expandDelayedWrappedExpressions = expandDelayedWrappedExpressions;
        this.recurseIntoScopeVariables = recurseIntoScopeVariables;
        this.translateAgain = translateAgain;
        this.modificationTimesHandler = modificationTimesHandler;
    }

    public static class Builder implements TranslationMap.Builder {

        private final Map<Variable, Variable> variables = new HashMap<>();
        private final Map<Expression, Expression> expressions = new HashMap<>();
        private final Map<Variable, Expression> variableExpressions = new HashMap<>();
        private final Map<MethodInfo, MethodInfo> methods = new HashMap<>();
        private final Map<Statement, List<Statement>> statements = new HashMap<>();
        private final Map<ParameterizedType, ParameterizedType> types = new HashMap<>();
        private ModificationTimesHandler modificationTimesHandler;
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
            return new TranslationMapImpl(statements, expressions, variableExpressions, variables, methods, types,
                    modificationTimesHandler,
                    expandDelayedWrappedExpressions, recurseIntoScopeVariables, yieldIntoReturn, translateAgain);
        }

        @Override
        public Builder setTranslateAgain(boolean translateAgain) {
            this.translateAgain = translateAgain;
            return this;
        }

        @Override
        public Builder setRecurseIntoScopeVariables(boolean recurseIntoScopeVariables) {
            this.recurseIntoScopeVariables = recurseIntoScopeVariables;
            return this;
        }

        @Override
        public Builder put(Statement template, Statement actual) {
            statements.put(template, List.of(actual));
            return this;
        }

        @Override
        public Builder put(MethodInfo template, MethodInfo actual) {
            methods.put(template, actual);
            return this;
        }

        @Override
        public Builder put(Statement template, List<Statement> statements) {
            this.statements.put(template, statements);
            return this;
        }

        @Override
        public Builder put(Expression template, Expression actual) {
            this.expressions.put(template, actual);
            return this;
        }

        @Override
        public Builder addVariableExpression(Variable variable, Expression actual) {
            variableExpressions.put(variable, actual);
            return this;
        }

        @Override
        public Builder renameVariable(Variable variable, Expression actual) {
            variableExpressions.put(variable, actual);
            return this;
        }

        @Override
        public Builder put(ParameterizedType template, ParameterizedType actual) {
            types.put(template, actual);
            return this;
        }

        @Override
        public TranslationMap.Builder put(Variable template, Variable actual) {
            variables.put(template, actual);
            return this;
        }

        @Override
        public Builder setYieldToReturn(boolean b) {
            this.yieldIntoReturn = b;
            return this;
        }

        @Override
        public Builder setExpandDelayedWrapperExpressions(boolean expandDelayedWrappedExpressions) {
            this.expandDelayedWrappedExpressions = expandDelayedWrappedExpressions;
            return this;
        }

        @Override
        public boolean translateMethod(MethodInfo methodInfo) {
            return methods.containsKey(methodInfo);
        }

        @Override
        public Builder setModificationTimesHandler(ModificationTimesHandler modificationTimesHandler) {
            this.modificationTimesHandler = modificationTimesHandler;
            return this;
        }

        @Override
        public boolean isEmpty() {
            return statements.isEmpty()
                   && expressions.isEmpty()
                   && variables.isEmpty()
                   && methods.isEmpty()
                   && types.isEmpty()
                   && variableExpressions.isEmpty();
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

    @Override
    public boolean expandDelayedWrappedExpressions() {
        return expandDelayedWrappedExpressions;
    }

    @Override
    public String toString() {
        return "TM{" + variables.size() + "," + methods.size() + "," + expressions.size() + "," + statements.size()
               + "," + types.size() + "," + localVariables.size() + "," + variableExpressions.size() +
               (expandDelayedWrappedExpressions ? ",expand" : "") + "}";
    }

    @Override
    public boolean translateYieldIntoReturn() {
        return yieldIntoReturn;
    }

    @Override
    public boolean hasVariableTranslations() {
        return !variables.isEmpty();
    }

    @Override
    public boolean recurseIntoScopeVariables() {
        return recurseIntoScopeVariables;
    }

    @Override
    public Expression translateExpression(Expression expression) {
        return Objects.requireNonNullElse(expressions.get(expression), expression);
    }

    @Override
    public MethodInfo translateMethod(MethodInfo methodInfo) {
        return methods.getOrDefault(methodInfo, methodInfo);
    }

    @Override
    public Variable translateVariable(Variable variable) {
        Variable v = variables.get(variable);
        if (v != null) return v;
        if (variable instanceof FieldReference fr && fr.scopeVariable() != null) {
            Variable scopeTranslated = translateVariable(fr.scopeVariable());
            if (scopeTranslated != fr.scopeVariable()) {
                Expression e = new VariableExpressionImpl(fr.source(), fr.comments(), scopeTranslated, null);
                return new FieldReferenceImpl(fr.fieldInfo(), e, scopeTranslated, fr.fieldInfo().type());
            }
        }
        return variable;
    }

    @Override
    public Expression translateVariableExpressionNullIfNotTranslated(Variable variable) {
        return variableExpressions.get(variable);
    }

    @Override
    public List<Statement> translateStatement(Statement statement) {
        List<Statement> list = statements.get(statement);
        return list == null ? List.of(statement) : list;
    }

    @Override
    public ParameterizedType translateType(ParameterizedType parameterizedType) {
        ParameterizedType inMap = types.get(parameterizedType);
        if (inMap != null) return inMap;
        List<ParameterizedType> params = parameterizedType.parameters();
        List<ParameterizedType> translatedTypes = params.isEmpty() ? params :
                params.stream().map(this::translateType).collect(toList(params));
        if (params == translatedTypes) return parameterizedType;
        return new ParameterizedTypeImpl(parameterizedType.typeInfo(), null, translatedTypes,
                parameterizedType.arrays(), parameterizedType.wildcard());
    }

    @Override
    public boolean isEmpty() {
        return statements.isEmpty() && expressions.isEmpty() && methods.isEmpty() &&
               types.isEmpty() && variables.isEmpty() && localVariables.isEmpty() && variableExpressions.isEmpty();
    }

    @Override
    public Map<? extends Variable, ? extends Variable> variables() {
        return variables;
    }

    @Override
    public Map<? extends Expression, ? extends Expression> expressions() {
        return expressions;
    }

    @Override
    public Map<? extends Variable, ? extends Expression> variableExpressions() {
        return variableExpressions;
    }

    @Override
    public Map<MethodInfo, MethodInfo> methods() {
        return methods;
    }

    @Override
    public Map<ParameterizedType, ParameterizedType> types() {
        return types;
    }

    @Override
    public Map<? extends Statement, List<Statement>> statements() {
        return statements;
    }

    @Override
    public boolean translateAgain() {
        return translateAgain;
    }

    @Override
    public String modificationTimes(Expression methodCallBeforeTranslation,
                                    Expression translatedObject, List<Expression> translatedParameters) {
        if (modificationTimesHandler == null) return null;
        // type cast: see interface spec: methodCallBeforeTranslation is of type Expression to avoid cyclic type dependencies
        MethodCall beforeTranslation;
        if ((beforeTranslation = methodCallBeforeTranslation.asInstanceOf(MethodCall.class)) != null) {
            return modificationTimesHandler.modificationTimes(beforeTranslation, translatedObject, translatedParameters);
        }
        throw new UnsupportedOperationException();
    }
}
