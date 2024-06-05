package org.e2immu.cstimpl.info;

import org.e2immu.cstapi.type.TypeNature;
import org.e2immu.cstimpl.output.Keyword;

public enum TypeNatureEnum implements TypeNature {

    ANNOTATION(Keyword.AT_INTERFACE),
    CLASS(Keyword.CLASS),
    ENUM(Keyword.ENUM),
    INTERFACE(Keyword.INTERFACE),
    PRIMITIVE(null),
    RECORD(Keyword.RECORD);

    private final Keyword keyword;

    TypeNatureEnum(Keyword keyword) {
        this.keyword = keyword;
    }

    public boolean isFinal() {
        return this != CLASS && this != INTERFACE;
    }

    @Override
    public boolean isClass() {
        return false;
    }

    @Override
    public boolean isInterface() {
        return false;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    public Keyword keyword() {
        return keyword;
    }
}
