package de.splotycode.bamboo.html.parser.attriute;

import lombok.Getter;

@Getter
public abstract class ValueAttribute<T> extends Attribute {

    private int valueStart, valueEnd;

    public ValueAttribute(String name) {
        super(name);
    }

    public abstract void setValue(T value);
    public abstract T getValue();
    public abstract String getStringValue();

    public void setValueBounds(int start, int end) {
        valueStart = start;
        valueEnd = end;
    }

}
