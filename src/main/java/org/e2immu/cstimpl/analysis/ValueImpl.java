package org.e2immu.cstimpl.analysis;

import org.e2immu.cstapi.analysis.Value;
import org.e2immu.cstapi.expression.Expression;
import org.e2immu.cstapi.info.FieldInfo;
import org.e2immu.cstapi.info.ParameterInfo;
import org.e2immu.cstapi.util.ParSeq;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValueImpl implements Value {

    public static class BoolImpl implements Value.Bool {
        public static final Bool FALSE = new BoolImpl(false);
        public static final Bool TRUE = new BoolImpl(true);
        private final boolean value;

        private BoolImpl(boolean value) {
            this.value = value;
        }

        @Override
        public boolean isFalse() {
            return !value;
        }


        @Override
        public boolean isTrue() {
            return value;
        }
    }

    public record ParameterParSeqImpl(ParSeq<ParameterInfo> parSeq) implements Value.ParameterParSeq {
        public static ParameterParSeqImpl EMPTY = new ValueImpl.ParameterParSeqImpl(new ParSeq<>() {
            @Override
            public boolean containsParallels() {
                return false;
            }

            @Override
            public <X> List<X> sortParallels(List<X> items, Comparator<X> comparator) {
                return items;
            }
        });
    }

    public record CommutableDataImpl(String seq, String par, String multi) implements CommutableData {
        public static final CommutableData BLANK = new ValueImpl.CommutableDataImpl("", "", "");
    }

    public record ImmutableImpl(int value) implements Immutable {
        public static final Immutable MUTABLE = new ImmutableImpl(0);

        @Override
        public boolean isAtLeastImmutableHC() {
            return value > 10; // FIXME
        }
    }

    public record AssignedToFieldImpl(Set<FieldInfo> fields) implements AssignedToField {
        public static final AssignedToField EMPTY = new AssignedToFieldImpl(Set.of());
    }

    public record PostConditionsImpl(Map<String, Expression> byIndex) implements PostConditions {
        public static final PostConditions EMPTY = new PostConditionsImpl(Map.of());
    }

    public record PreconditionImpl(Expression expression) implements Precondition {
        public static final Precondition EMPTY = new PreconditionImpl(null);
    }
}
