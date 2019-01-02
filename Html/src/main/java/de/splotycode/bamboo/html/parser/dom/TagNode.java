package de.splotycode.bamboo.html.parser.dom;

import lombok.Getter;
import lombok.Setter;

public class TagNode extends Node {

    @Setter @Getter private int lastEnd = -1;

    @Getter private int endTagStart = -1, endTagEnd = -1;

    public TagNode(String name, int start, int end) {
        super(name, start, end);
    }

    public TagNode(String name, Node parent, int start, int end) {
        super(name, parent, start, end);
    }

    public void setTagEndBounds(int start, int end) {
        endTagStart = start;
        endTagEnd = end;
    }

    @Override
    public int getAbsoluteEnd() {
        if (endTagEnd != -1) return endTagEnd;
        if (lastEnd != -1) return lastEnd;
        return super.getAbsoluteEnd();
    }
}
