package de.splotycode.bamboo.html.util;

public enum AlmostBoolean {

    YES,
    NO,
    MAYBE;

    public static final AlmostBoolean fromBoolean(final boolean value) {
        return value ? YES : NO;
    }

    public boolean decide(boolean optimistically) {
        return optimistically?this != NO:this == YES;
    }

}