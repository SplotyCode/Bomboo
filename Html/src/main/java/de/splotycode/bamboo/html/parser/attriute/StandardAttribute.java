package de.splotycode.bamboo.html.parser.attriute;

import de.splotycode.bamboo.html.util.AlmostBoolean;
import de.splotycode.bamboo.html.util.StringUtil;

public class StandardAttribute extends ValueAttribute<String> {

    private String value;
    private AlmostBoolean isFloat;
    private boolean floatValueCached;
    private float floatValue;

    public StandardAttribute(String name, String value) {
        super(name);
        this.value = value;
        isFloat = AlmostBoolean.MAYBE;
        floatValueCached = false;
    }

    public boolean isFloat() {
        if(isFloat == AlmostBoolean.MAYBE){
            if(StringUtil.isFloat(value)) isFloat = AlmostBoolean.YES;
            else isFloat = AlmostBoolean.NO;
        }
        return isFloat.decide(false);
    }

    public float floatValue(){
        if(floatValueCached)
            floatValue = StringUtil.toFloat(value);
        return floatValue;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
        isFloat = AlmostBoolean.MAYBE;
        floatValueCached = false;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getStringValue() {
        return value;
    }
}
