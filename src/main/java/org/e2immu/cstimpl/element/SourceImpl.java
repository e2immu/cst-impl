package org.e2immu.cstimpl.element;

import org.e2immu.cstapi.element.Source;
import org.e2immu.cstapi.info.Info;

public class SourceImpl implements Source {
    private final Info info;
    private final int beginLine;
    private final int beginPos;
    private final int endLine;
    private final int endPos;

    public SourceImpl(Info info, int beginLine, int beginPos, int endLine, int endPos) {
        this.info = info;
        this.beginLine = beginLine;
        this.beginPos = beginPos;
        this.endLine = endLine;
        this.endPos = endPos;
    }

    @Override
    public int compareTo(Source o) {
        if (o instanceof SourceImpl s) {
            int bl = beginLine - s.beginLine;
            if (bl != 0) return bl;
            int bp = beginPos - s.beginPos;
            if (bp != 0) return bp;
            int el = endLine - s.endLine;
            if (el != 0) return el;
            return endPos - endLine;
        } else throw new UnsupportedOperationException();
    }

    public Info info() {
        return info;
    }

    public int beginLine() {
        return beginLine;
    }

    public int beginPos() {
        return beginPos;
    }

    public int endLine() {
        return endLine;
    }

    public int endPos() {
        return endPos;
    }
}
