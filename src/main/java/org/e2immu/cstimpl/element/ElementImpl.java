package org.e2immu.cstimpl.element;

import org.e2immu.cstapi.element.Element;
import org.e2immu.cstapi.info.TypeInfo;
import org.e2immu.cstimpl.output.QualificationImpl;

public abstract class ElementImpl implements Element {

    public record TypeReference(TypeInfo typeInfo, boolean explicit) implements Element.TypeReference {
    }

    @Override
    public String toString() {
        return print(QualificationImpl.SIMPLE_NAMES).toString();
    }
}
