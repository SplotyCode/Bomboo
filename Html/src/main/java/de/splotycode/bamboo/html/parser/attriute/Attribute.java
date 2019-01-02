package de.splotycode.bamboo.html.parser.attriute;

import lombok.Getter;

@Getter
public class Attribute {

    private final String name;
    private int nameStart, nameEnd;

    public Attribute(String name) {
        this.name = name;
    }

    public void setNameBounds(int start, int end) {
        nameStart = start;
        nameEnd = end;
    }

}
